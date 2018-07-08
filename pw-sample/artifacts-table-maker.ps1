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
$artifactList | %{
    echo "��������������"
    echo $_
    echo "��������������"
}

############################################################
# 4 ���܂Ŏ擾���������}�[�W
############################################################
function marge($_artifactList, $_artifactNameList, $_urlList) {
    $_artifactList | %{ 
        $name = $_.filename
        
        # ���ʕ��ɑ΂��郊���N�����擾����
        $url =$_artifactNameList | `
            ?{ $_.filename -eq $name } | `
            %{
                # 2.�T���v��������URL���擾
                $sampleUrls = $_.samples | %{
                    $sampleName = $_
                    $_urlList | ?{$_.filename -eq $sampleName}
                }
                # 3.�t�H�[�}�b�g������URL���擾
                $formatUrls = $_.formats | %{
                    $formatName = $_
                    $_urlList | ?{$_.filename -eq $formatName}
                }

                @{
                    samples=$sampleUrls;
                    formats=$formatUrls;
                }
            }

        # ���ʕ�����URL����t�^
        # $_.add('url', $url)
        $_
    }
}


$marged = marge ($artifactList, $artifactRelationArray, $filenameAndUrlMap)


$marged | %{
    echo "��������������������������"
    echo $_
    echo "������������������������"

}


# �w�肵���f�B���N�g���z���̃G�N�Z���t�@�C�����J���āA�G�N�Z���u�b�N�̃��X�g��Ԃ�
function openExcels($dirPath) {
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { $_.FullName } |
        % { $excel.Workbooks.Open($_) }
}

# $booklist = openExcels "C:\Users\Takuto\Desktop\sample"

# foreach ($book in $booklist) {
#     $old = $book.Sheets(1).Cells.Item(1,1).value2
#     If([string]::IsNullOrEmpty($old)) { 
#         Write-Output "����ł�"
#     }

#     $book.Save()
# }




# $book = $excel.Workbooks.Open("file:///C:\Users\Takuto\Desktop\sample");

# $book.Sheets.Count # �V�[�g���J�E���g
# $book.ActiveSheet.Name # �A�N�e�B�u�ɂȂ��Ă���V�[�g�����擾
# $book.Sheets(1).Name # �ŏ��̃V�[�g�����擾
# $book.Sheets(1).Name = "hoge"

# $book.Sheets(1).Name # �ŏ��̃V�[�g�����擾

# $sheet = $book.Sheets("hoge") # ��舵���₷���悤�ɃV�[�g�擾���܂�
# $sheet.Name
# $sheet.Cells.Item(1,1) = 100 # �Z��A1��100����
# $sheet.Cells.Item(1,2) = 50 # �Z����B1��50����
# $sheet.Range("A2", "B2") = 50 # A2����B2�܂�50����
# $sheet.Range("A3", "B3") = 5, 10 # A3��5 B3��10����
# $sheet.Range("A2").Text
# # �V�[�g�̎��o��
# # $sheet = $excel.Worksheets.Item("Sheet1")
# $sheet.Range("C1") = "=SUM(A1:B3)"
# $sheet.Range("C1").Text # C3 �̃e�L�X�g��\��
# $sheet.Range("C1").Formula # C3�̌v�Z����\��

# $sheet.Range("C1").copy($sheet.Range("C2:C3")) # �v�Z���̃R�s�[

# $sheet.Range("C1:C3").Font.Bold = $true # �����ɂ���
# $sheet.Range("C1:C3").interior.ColorIndex = 3 # �Z����ԐF�ɂ���
# $sheet.Range("C1:C3").Font.ColorIndex = 2 # �����𔒐F�ɂ���

# $sheet.Range("C1:C3") | Get-Member
# $sheet.Range("A1").AddComment("�ق��ق��@�ӂӂ�")
# $book.SaveAs("${HOME}\Desktop\hoge.xlsx")
# $excel.Quit() # Excel�̏C��

# [System.Runtime.Interopservices.Marshal]::ReleaseComObject($excel) # �ϐ��̔j��
# [System.Runtime.Interopservices.Marshal]::ReleaseComObject($sheet) # �ϐ��̔j��