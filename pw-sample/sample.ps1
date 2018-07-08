echo 'sample' + ' sample'
echo 'sample' + ' sample'
echo 'sample' + ' sample'
echo 'sample' + ' sample'
echo 'sample' + ' sample'
echo 'sample' + ' sample'

[string] ${みんなの　変数} = "HgoeHgoe"

${みんなの　変数}.ToUpper()

$a = 'gege'

"ほんで　$a `tタブのあと`n改行のあと"
"ほんで　${a}too many"
echo ('ほんで $a' + 'うううん')
[int] $hoge = 10

$fuga = "aaa"

$hoge.GetType()
$hoge
$fuga.GetType()
$fuga
<#
複数行のコメントだよ
#>

[Object[]]$array = 'one', 'two', 'three'
$array.GetType().Name
$array[0]

$array.Length

$array2 = @()
$array2.Length

# $array3 = @(1..10)
$array3 = 1..10

foreach ($e in $array3) {
    echo $e
}

$map = @{
  hoge="HOGE";
  fuga="FUGA";
  piyo="PIYO"
}

$map.GetType().Name
$map["hoge"]
$map.Item('fuga')


$list = New-Object System.Collections.ArrayList

$list.Add('hoge')
$list.Add('fuga') > $null
[void]$list.Add('piyo')

echo ($list[0] + ", " + $list[1] + ", " + $list[2])

for ($i = 0; $i -le 3; $i++) {
    echo "今は${i}番目"
}


$i = 0

while ($i -lt 3) {
    echo $i
    $i++
}

if($true) {
    echo 'hoge'
}

if ($false) {
    echo 'ddd'
} else {
    echo 'ffff'
}


# 空文字と0はfalseとみなされる




if (0) {
  echo '0'
} elseif ('') {
  echo ''
} elseif (1) {
  echo 1
} else {
  echo 'else'
}

'hoge' -eq 'hoge'
10 -gt 10
10 -ge 10
'hoge' -like 'h*'
'dd' -notlike 'h'

($true -or $false) 
($true -and $true -and ($true -or $false) )
(-not $false -and -! $false)
($true -and $true -and ($true -or $false) -and -not $false -and -! $false)


$num = 5
switch($num) {
  1 {
    echo "hoge"
  }
  5 {
    echo "fuga"
  }

  10 {
    echo "piyo"
  }
}

$str = "hoge"
$str1 = "hoge"
$str2 = "fuga"
switch ($str) {
    $str1 {
      echo "HOGE1"
    }
    $str2 {
      echo "HOGE2"
    }
}


switch -Wildcard ("hoge") {
  "h*" {
    echo "HOGE wild 1"
  }
  "*g*" {
    echo "HOGE wild 2"
  }
}

switch -Regex ("hoge123") {
  "hoge\d+$" {
    echo "hoge regex"
  }
}



$args.Length
$args


function Hoge($a, $b) {
    echo "hoge $a $b" 
}

function Fuga {
    foreach($arg in $args) {
        echo $arg
    }
}

function Piyo($a, $b = 1000) {
    echo "$a $b"
}


Hoge 1 'baba'
Fuga 1,3,4 'bababa' 'hoge'
Piyo 12
Piyo 12 10000


function Foo($a, $b) {
  return $a,$b
}

$result = Foo -b "bbbb" -a "fdafda"
echo ('$result[0] = ' + $result[0])
echo ('$result[1] = ' + $result[1])


function craeteMap($key, $value) {
  return @{$key = $value;}
}

$resultMap = craeteMap 'key1' 'value1'

echo ('$resultMap.Item(key1) = ' + $resultMap.Item('key1'))


$a



# ヒアドキュメント
$dddd=@"
gdafad
$a
a
どこでもドア
"@


$dddd




$a = "aaaa"

function Zoo($b = "bbb") {
  echo ('$a = ' + $a + '@Zoo first')
  echo ('$b = ' + $b + '@Zoo first')
  echo ('$c = ' + $c + '@Zoo first')

  $a = "inner a"
  $c = 'inner c'
  echo ('$a = ' + $a + '@Zoo last')
  echo ('$b = ' + $b + '@Zoo last')
  echo ('$c = ' + $c + '@Zoo last')

}

Zoo

echo ('$a = ' + $a)
echo ('$b = ' + $b)
echo ('$c = ' + $c)


$ErrorActionPreference = "stop"
# 定数を宣言する
# set CONSTANT_VALUES "const-hoge" -Option Constant

try {
    Get-Content "not exists"
} catch {
    echo "catch start"
    echo $error[0]
    echo "catch end"
}

echo $CONSTANT_VALUES






