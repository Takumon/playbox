############################################################
# 1 成果物とURLのマッピング情報を取得
############################################################

# URLエンコード用
Add-Type -AssemblyName System.Web

# 指定したディレクトリ配下のファイルについて、
# 単純ファイル名とURLの連想配列を取得する
function getFileNameAndUrlMap($dirPath, $urlRoot) {
    $resultMap = @{}
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { 
            Push-Location $dirPath
                $relativePath = Resolve-Path $_.FullName -Relative
            Pop-Location
            $url = $urlRoot + '/' + [System.Web.HttpUtility]::UrlEncode($relativePath.Replace('.\', '')).Replace('%5c', '/')
            $resultMap.Add($_.Name, $url)
        }
    
    return $resultMap
}

$artifactsPath = ".\resources\成果物"
$urlRoot = "https://hogehoge.com/wiki"
$filenameAndUrlMap = getFileNameAndUrlMap $artifactsPath $urlRoot

############################################################
# 2 成果物とサンプル、フォーマットのマッピング情報を取得
############################################################

# 成果物名とサンプル、フォーマットの紐づけをしたマップを取得する
function getArticatsRelation(
    $path, 
    $rn_start = 4,
    $cn_filename = 1,
    $cn_sample = 2,
    $cn_format = 3,
    $delimiter = ','
) {

    $excel = New-Object -ComObject Excel.Application
    $excel.Visible = $false
    $sheet = $excel.Workbooks.Open($path).Sheets(1)


    $result = @()
    for ($rn = $rn_start; $sheet.Cells.Item($rn,$cn_filename).Text ; $rn++) {

        $result += @{
            filename = $sheet.Cells.Item($rn,$cn_filename).Text;
            samples = $sheet.Cells.Item($rn,$cn_sample).Text.Split($delimiter);
            formats = $sheet.Cells.Item($rn,$cn_format).Text.Split($delimiter);
        }
    }


    $excel.Quit()
    return $result
}

$artifactRelationExcelPath = "file:///C:\Users\Takuto\workspace\git\playbox\pw-sample\resources\成果物紐づけ.xlsx"
$artifactRelationArray = getArticatsRelation($artifactRelationExcelPath)

############################################################
# 3 成果物一覧情報を取得
############################################################

function getArtifactList(
    $path,
    $rn_start = 4,
    $cn_category1 = 1,
    $cn_category2 = 2,
    $cn_filename = 3,
    $cn_discription = 4
) {
    $excel = New-Object -ComObject Excel.Application
    $excel.Visible = $false
    $sheet = $excel.Workbooks.Open($path).Sheets(1)
    

    $result = @()
    for ($rn = $rn_start; $sheet.Cells.Item($rn,$cn_filename).Text ; $rn++) {

        $result += @{
            category1 = $sheet.Cells.Item($rn,$cn_category1).Text;
            category2 = $sheet.Cells.Item($rn,$cn_category2).Text;
            filename = $sheet.Cells.Item($rn,$cn_filename).Text;
            discription = $sheet.Cells.Item($rn,$cn_discription).Text;
        }
    }

    $excel.Quit()
    return $result
}

$artifactListExcelPath = "file:///C:\Users\Takuto\workspace\git\playbox\pw-sample\resources\成果物一覧.xlsx"
$artifactList = getArtifactList($artifactListExcelPath)

############################################################
# 4 今まで取得した情報をマージ
############################################################
$marged = $artifactList | %{ 
    $name = $_.filename
        
    # 成果物に対するリンク情報を取得する
    $url =$artifactRelationArray | `
        ?{ $_.filename -eq $name } | `
        %{
            @{
                # 2.サンプル名からURLを取得
                samples=$_.samples | %{ $filenameAndUrlMap.Item($_) };
                # 3.フォーマット名からURLを取得
                formats=$_.formats | %{ $filenameAndUrlMap.Item($_) };
            }
        }

    # 成果物情報にURL情報を付与
    $_.add('samplesUrl', $url.samples)
    $_.add('formatsUrl', $url.formats)
    $_
}


############################################################
# 5 マージした情報をWikiのテーブル形式に変換
############################################################
function craeteLinks($urls) {
    # リンクが一つの場合
    if ($urls -is [System.String]) { 
        return "|| [$($urls) リンク]"
    }
    
    # リンクが複数の場合
    if ($urls -and $urls.Length) { 
        $record += "||"
        for($i = 0; $i -lt $urls.Length; $i++) {
            $record += "[$($urls[$i]) リンク$($i + 1)]"

            if ($i -ne ($urls.Length -1)) {
                $record += " "
            }
        }
        return $record
    }
    
     # リンクが存在しない場合
     return "|| "
}


$tableContent = $marged | %{
    $record = "|-`r`n"
    $record += "|$($_.category1)||$($_.category2)||$($_.filename)||$($_.discription)"
    $record += craeteLinks($_.samplesUrl)
    $record += craeteLinks($_.fromatsUrl)
    $record += "|| 備考とか"
    $record
}

$table = @"
{|
|大カテゴリ
|小カテゴリ
|成果物名
|サンプル
|フォーマット
$($tableContent -join "`r`n")
|}
"@


###############################################################
# 6 結果をファイル出力
###############################################################

$sysdate = Get-Date

$fileName = ".\wiki-table_$($sysdate.ToString("yyyyMMddHHmmss")).txt"
$file = New-Object System.IO.StreamWriter($fileName, $false, [System.Text.Encoding]::GetEncoding("sjis"))
$file.Write($table)
$file.Close()
