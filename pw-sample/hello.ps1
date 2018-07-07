# URL�G���R�[�h�p
Add-Type -AssemblyName System.Web

# Excel�I�u�W�F�N�g�̐���
$excel = New-Object -ComObject Excel.Application
$excel.Visible = $true

# �w�肵���f�B���N�g���z���̃G�N�Z���t�@�C�����J���āA�G�N�Z���u�b�N�̃��X�g��Ԃ�
function openExcels($dirPath) {
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { $_.FullName } |
        % { $excel.Workbooks.Open($_) }
}

# �w�肵���f�B���N�g���z���̃t�@�C���ɂ��āA
# �P���t�@�C������URL�̘A�z�z����擾����
function getFileNameAndUrlMap($dirPath, $urlRoot) {
    $result = @{}
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { 
            Push-Location $dirPath
                $relativePath = Resolve-Path $_.FullName -Relative
            Pop-Location
            $url = $urlRoot + '/' + [System.Web.HttpUtility]::UrlEncode($relativePath.Replace('.\', '')).Replace('%5c', '/')
            $result.Add($_.Name, $url)
        }
    
    $result
}

$filenameAndUrlMap = getFileNameAndUrlMap "C:\Users\Takuto\Desktop\sample" "https://hogehoge.com/wiki"

# ���l�[������
Get-ChildItem "C:\Users\Takuto\Desktop\sample2" | Rename-Item -NewName { $_.name -Replace '^�V�K Microsoft Word ���� (\d+)','���j���[�A��_�G�N�Z��.$1'}


# $booklist = openExcels "C:\Users\Takuto\Desktop\sample"

# foreach ($book in $booklist) {
#     $old = $book.Sheets(1).Cells.Item(1,1).value2
#     If([string]::IsNullOrEmpty($old)) { 
#         Write-Output "����ł�"
#     }

#     $book.Save()
# }

$excel.Quit() # Excel�̏C��



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