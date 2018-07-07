# URLエンコード用
Add-Type -AssemblyName System.Web

# Excelオブジェクトの生成
$excel = New-Object -ComObject Excel.Application
$excel.Visible = $true

# 指定したディレクトリ配下のエクセルファイルを開いて、エクセルブックのリストを返す
function openExcels($dirPath) {
    Get-ChildItem $dirPath -Recurse -Include *.xlsx |
        % { $_.FullName } |
        % { $excel.Workbooks.Open($_) }
}

# 指定したディレクトリ配下のファイルについて、
# 単純ファイル名とURLの連想配列を取得する
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

# リネーム処理
Get-ChildItem "C:\Users\Takuto\Desktop\sample2" | Rename-Item -NewName { $_.name -Replace '^新規 Microsoft Word 文書 (\d+)','リニューアル_エクセル.$1'}


# $booklist = openExcels "C:\Users\Takuto\Desktop\sample"

# foreach ($book in $booklist) {
#     $old = $book.Sheets(1).Cells.Item(1,1).value2
#     If([string]::IsNullOrEmpty($old)) { 
#         Write-Output "からです"
#     }

#     $book.Save()
# }

$excel.Quit() # Excelの修了



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