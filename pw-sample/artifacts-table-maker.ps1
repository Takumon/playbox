############################################################
# 1 ���ʕ���URL�̃}�b�s���O�����擾
############################################################

# URL�G���R�[�h�p
Add-Type -AssemblyName System.Web

# �w�肵���f�B���N�g���z���̃t�@�C���ɂ��āA
# �P���t�@�C������URL�̘A�z�z����擾����
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

$artifactsPath = ".\resources\���ʕ�"
$urlRoot = "https://hogehoge.com/wiki"
$filenameAndUrlMap = getFileNameAndUrlMap $artifactsPath $urlRoot

############################################################
# 2 ���ʕ��ƃT���v���A�t�H�[�}�b�g�̃}�b�s���O�����擾
############################################################

# ���ʕ����ƃT���v���A�t�H�[�}�b�g�̕R�Â��������}�b�v���擾����
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

$artifactRelationExcelPath = "file:///C:\Users\Takuto\workspace\git\playbox\pw-sample\resources\���ʕ��R�Â�.xlsx"
$artifactRelationArray = getArticatsRelation($artifactRelationExcelPath)

############################################################
# 3 ���ʕ��ꗗ�����擾
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

$artifactListExcelPath = "file:///C:\Users\Takuto\workspace\git\playbox\pw-sample\resources\���ʕ��ꗗ.xlsx"
$artifactList = getArtifactList($artifactListExcelPath)

############################################################
# 4 ���܂Ŏ擾���������}�[�W
############################################################
$marged = $artifactList | %{ 
    $name = $_.filename
        
    # ���ʕ��ɑ΂��郊���N�����擾����
    $url =$artifactRelationArray | `
        ?{ $_.filename -eq $name } | `
        %{
            @{
                # 2.�T���v��������URL���擾
                samples=$_.samples | %{ $filenameAndUrlMap.Item($_) };
                # 3.�t�H�[�}�b�g������URL���擾
                formats=$_.formats | %{ $filenameAndUrlMap.Item($_) };
            }
        }

    # ���ʕ�����URL����t�^
    $_.add('samplesUrl', $url.samples)
    $_.add('formatsUrl', $url.formats)
    $_
}


############################################################
# 5 �}�[�W��������Wiki�̃e�[�u���`���ɕϊ�
############################################################
function craeteLinks($urls) {
    # �����N����̏ꍇ
    if ($urls -is [System.String]) { 
        return "|| [$($urls) �����N]"
    }
    
    # �����N�������̏ꍇ
    if ($urls -and $urls.Length) { 
        $record += "||"
        for($i = 0; $i -lt $urls.Length; $i++) {
            $record += "[$($urls[$i]) �����N$($i + 1)]"

            if ($i -ne ($urls.Length -1)) {
                $record += " "
            }
        }
        return $record
    }
    
     # �����N�����݂��Ȃ��ꍇ
     return "|| "
}


$tableContent = $marged | %{
    $record = "|-`r`n"
    $record += "|$($_.category1)||$($_.category2)||$($_.filename)||$($_.discription)"
    $record += craeteLinks($_.samplesUrl)
    $record += craeteLinks($_.fromatsUrl)
    $record += "|| ���l�Ƃ�"
    $record
}

$table = @"
{|
|��J�e�S��
|���J�e�S��
|���ʕ���
|�T���v��
|�t�H�[�}�b�g
$($tableContent -join "`r`n")
|}
"@


###############################################################
# 6 ���ʂ��t�@�C���o��
###############################################################

$sysdate = Get-Date

$fileName = ".\wiki-table_$($sysdate.ToString("yyyyMMddHHmmss")).txt"
$file = New-Object System.IO.StreamWriter($fileName, $false, [System.Text.Encoding]::GetEncoding("sjis"))
$file.Write($table)
$file.Close()
