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
$artifactList | %{
    echo "▼▼▼▼▼▼▼"
    echo $_
    echo "▲▲▲▲▲▲▲"
}

############################################################
# 4 今まで取得した情報をマージ
############################################################
function marge($_artifactList, $_artifactNameList, $_urlList) {
    $_artifactList | %{ 
        $name = $_.filename
        
        # 成果物に対するリンク情報を取得する
        $url =$_artifactNameList | `
            ?{ $_.filename -eq $name } | `
            %{
                # 2.サンプル名からURLを取得
                $sampleUrls = $_.samples | %{
                    $sampleName = $_
                    $_urlList | ?{$_.filename -eq $sampleName}
                }
                # 3.フォーマット名からURLを取得
                $formatUrls = $_.formats | %{
                    $formatName = $_
                    $_urlList | ?{$_.filename -eq $formatName}
                }

                @{
                    samples=$sampleUrls;
                    formats=$formatUrls;
                }
            }

        # 成果物情報にURL情報を付与
        # $_.add('url', $url)
        $_
    }
}


$marged = marge ($artifactList, $artifactRelationArray, $filenameAndUrlMap)


$marged | %{
    echo "●●●●●●▼▼▼▼▼▼▼"
    echo $_
    echo "▲▲▲▲▲▲▲●●●●●"

}


# 指定したディレクトリ配下のエクセルファイルを開いて、エクセルブックのリストを返す
function openExcels($dirPath) {
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { $_.FullName } |
        % { $excel.Workbooks.Open($_) }
}

# $booklist = openExcels "C:\Users\Takuto\Desktop\sample"

# foreach ($book in $booklist) {
#     $old = $book.Sheets(1).Cells.Item(1,1).value2
#     If([string]::IsNullOrEmpty($old)) { 
#         Write-Output "からです"
#     }

#     $book.Save()
# }




# $book = $excel.Workbooks.Open("file:///C:\Users\Takuto\Desktop\sample");

# $book.Sheets.Count # シート数カウント
# $book.ActiveSheet.Name # アクティブになっているシート名を取得
# $book.Sheets(1).Name # 最初のシート名を取得
# $book.Sheets(1).Name = "hoge"

# $book.Sheets(1).Name # 最初のシート名を取得

# $sheet = $book.Sheets("hoge") # 取り扱いやすいようにシート取得します
# $sheet.Name
# $sheet.Cells.Item(1,1) = 100 # セルA1に100を代入
# $sheet.Cells.Item(1,2) = 50 # セルのB1に50を代入
# $sheet.Range("A2", "B2") = 50 # A2からB2まで50を代入
# $sheet.Range("A3", "B3") = 5, 10 # A3に5 B3に10を代入
# $sheet.Range("A2").Text
# # シートの取り出し
# # $sheet = $excel.Worksheets.Item("Sheet1")
# $sheet.Range("C1") = "=SUM(A1:B3)"
# $sheet.Range("C1").Text # C3 のテキストを表示
# $sheet.Range("C1").Formula # C3の計算式を表示

# $sheet.Range("C1").copy($sheet.Range("C2:C3")) # 計算式のコピー

# $sheet.Range("C1:C3").Font.Bold = $true # 太字にする
# $sheet.Range("C1:C3").interior.ColorIndex = 3 # セルを赤色にする
# $sheet.Range("C1:C3").Font.ColorIndex = 2 # 文字を白色にする

# $sheet.Range("C1:C3") | Get-Member
# $sheet.Range("A1").AddComment("ほげほげ　ふふふ")
# $book.SaveAs("${HOME}\Desktop\hoge.xlsx")
# $excel.Quit() # Excelの修了

# [System.Runtime.Interopservices.Marshal]::ReleaseComObject($excel) # 変数の破棄
# [System.Runtime.Interopservices.Marshal]::ReleaseComObject($sheet) # 変数の破棄