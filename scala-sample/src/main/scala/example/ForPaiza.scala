import io.Source.stdin
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable

object Main {
  test()
  // producted()

  def producted(): Unit = {
   val lines = stdin.getLines().toList
   exec(lines)
  }

  def test(): Unit = {
    exec(List(
      "7 10 4",
      "1 8 1",
      "4 1 5",
      "1 6 2",
      "2 2 0"
    ))

    exec(List(
      "10 10 9",
    "2 2 4",
    "2 2 3",
    "2 2 5",
    "2 2 2",
    "2 2 6",
    "2 2 1",
    "2 2 7",
    "2 2 0",
    "2 2 8"
    ))
  }


  def exec(lines: List[String]): Unit = {
    val Array(boxH, boxW, blockCount) = lines(0).split(" ").map(_.toInt)


    val blockInfoList = lines.tail.map { line =>
      val Array(hight, width, x) = line.split(" ").map(_.toInt)
      (hight, width, x)
    }


    var box = Array.ofDim[Boolean](boxH, boxW)
    (0 until boxH) foreach { indexH =>
      (0 until boxW) foreach { indexW =>
        box(indexH)(indexW) = false
      }
    }


    blockInfoList.foreach(dropBlock(_, box))
  }

  def dropBlock(blockInfo: (Int, Int, Int), box: Array[Array[Boolean]]): Unit = {
    val (hight, width, x) = blockInfo

    println(blockInfo + " を落とすと↓")


    // 各高さに置いて、ブロックを許容する幅があるか
    val canSetInfoList = (0 until box.length).map {indexH =>
      // ブロックが占有する位置にすでにブロックがないか
      val canSet = (x to (x + width - 1)).forall {indexW =>
        !box(indexH)(indexW)
      }

      (indexH, canSet)
    }


    // どこまで下に行けるか

    // ブロックが領域を占有できない最初の行
    val bottomHIndex = canSetInfoList.find(!_._2) match {
      case Some(info) => info._1 - 1  // それの一個前が一番下
      case None => box.length - 1 // 一番下が最下
    }

    // ブロックを設置
    ((bottomHIndex - hight + 1) to bottomHIndex) foreach { indexH =>
      (x to (x + width - 1)) foreach { indexW =>
        box(indexH)(indexW) = true
      }
    }


    printBox(box)
    println()
  }

  def printBox(box: Array[Array[Boolean]]): Unit = {
    val boxStr = box.map { list =>
      list.map(value =>  if (value) "#" else  ".").mkString("")
    }.mkString("\n")
    println(boxStr)
  }
}









class Other {

  def ビーム反射(lines: List[String]): Unit = {
    val boxSize = (lines(0).split(" ")(1).toInt, lines(0).split(" ")(0).toInt) // (width, heigth)
    val boxMirrorPosition = lines.tail.map(_.toCharArray)

    // 1 →　
    // 2 ↓　
    // 3 ←　
    // 4 ↑
    var currentDirection = 1


    // (0, 0)に進んだところからスタート
    var currentPosition = (0, 0) // x, y
    var count = 0

    // 箱の外に出るまで回す
    while (!isOutOfBox(boxSize, currentPosition)) {

      count += 1
      // 鏡がある場合は進行方法を更新
      currentDirection = boxMirrorPosition(currentPosition._2).apply(currentPosition._1) match {
        case '/' =>
          directionAfterReflect(currentDirection, 1)
        case '\\' =>
          directionAfterReflect(currentDirection, 2)
        case '_' =>
          currentDirection
      }

      // 進行方向に従って進む
      currentPosition = nextPosition(currentDirection, currentPosition)
    }


    println(count)
  }


  // 幅がはみ出ているか    高さがはみ出ているか
  def isOutOfBox(boxSize: (Int, Int), position: (Int, Int)): Boolean =
    (position._1 < 0 || boxSize._1 <= position._1) || (position._2 < 0 || boxSize._2 <= position._2)

  // 1 →　
  // 2 ↓　
  // 3 ←　
  // 4 ↑
  def nextPosition(direction: Int, position: (Int, Int)): (Int, Int) = direction match {
    case 1 => (position._1 + 1, position._2     )
    case 2 => (position._1    , position._2 + 1 )
    case 3 => (position._1 - 1, position._2     )
    case 4 => (position._1    , position._2 -1  )
  }

  // direction
  // 1 →　
  // 2 ↓　
  // 3 ←　
  // 4 ↑
  // 鏡のdirection
  // 1 /
  // 2 \
  def directionAfterReflect(currentDirection: Int, mirrorDirection: Int): Int = mirrorDirection match {

    case 1 =>
      currentDirection match {
        case 1 => 4
        case 2 => 3
        case 3 => 2
        case 4 => 1
      }
    case 2 =>
      currentDirection match {
        case 1 => 2
        case 2 => 1
        case 3 => 4
        case 4 => 3
      }
  }



  val KeyPositions = Map(
    'q' -> (0, 0),
    'w' -> (0, 1),
    'e' -> (0, 2),
    'r' -> (0, 3),
    't' -> (0, 4),
    'y' -> (0, 5),
    'u' -> (0, 6),
    'i' -> (0, 7),
    'o' -> (0, 8),
    'p' -> (0, 9),

    'a' -> (1, 0),
    's' -> (1, 1),
    'd' -> (1, 2),
    'f' -> (1, 3),
    'g' -> (1, 4),
    'h' -> (1, 5),
    'j' -> (1, 6),
    'k' -> (1, 7),
    'l' -> (1, 8),

    'z' -> (2, 0),
    'x' -> (2, 1),
    'c' -> (2, 2),
    'v' -> (2, 3),
    'b' -> (2, 4),
    'n' -> (2, 5),
    'm' -> (2, 6),
  )

  val lines = stdin.getLines().toList
  exec(lines)




  def isLeft(c: Char): Boolean = {
    KeyPositions(c)._2 <= 4
  }


  def isRight(c: Char): Boolean =
    !isLeft(c)

  def isTouch(a: Char, b: Char): Boolean = {
    if (a == b) {
      true
    } else {
      val (posAX, posAY) = KeyPositions(a)
      val (posBX, posBY) = KeyPositions(b)

      val distance = (posAX - posBX) * (posAX - posBX) + (posAY - posBY) * (posAY - posBY)
      distance == 1
    }
  }

  def タイピングミス回数判定(lines: List[String]): Unit = {
    // 本来右か左どちらで打つかの判定
    val chars = lines(0).toCharArray

    // (文字列, 本来左ポジションか, 前のキーに隣接しているか)のリスト
    val charInfoList = (0 until chars.length) map {i =>
      val currentC = chars(i)
      if (i == 0)
        (currentC, isLeft(currentC), false)
      else
        (currentC, isLeft(currentC), isTouch(currentC, chars(i -1)) )
    }


    var currentPositionIsLeft = false
    var misCount = 0
    (0 until chars.length) foreach { i =>
      val (c, cIsLeft, cIsTouch) = charInfoList(i)

      if(cIsTouch) {
        // 前のキーを打ったのが左か = 今回のキーを打ったのが左か
        // ポジション間違い = 今回のキーを打ったのが左か == 本来今回のキーを左で打つか
        if (currentPositionIsLeft != cIsLeft) misCount += 1

        // ポジションの変更はなし
      } else {
        currentPositionIsLeft = cIsLeft
      }

    }

    println(misCount)
  }


  def アメンボの領域重複チェック(lines: List[String]): Unit = {

    val pointOfWaterStrider = lines(1).toInt
    val pointLines = lines.tail.tail

    // 湖の座標マップを作成
    val pointMap = pointLines.zipWithIndex.map {v =>
      val (line, index) = v
      val points = line.split(" ").map(_.toInt)
      val startPoint = (points(0), points(1))
      val endPoint = (points(2), points(3))
      index + 1 -> (startPoint, endPoint)
    }.toMap



    // それぞれの座標マップの重なりを整理
    var overlapMap = Map.empty[Int, mutable.ListBuffer[Int]]
    (1 to pointMap.size) foreach { pubbleNumber =>
      if (pubbleNumber != pointMap.size) {
        ((pubbleNumber + 1) to pointMap.size) foreach { otherPubbleNumber =>

          if(isOverlap(pointMap(pubbleNumber), pointMap(otherPubbleNumber))) {
            if (overlapMap.contains(pubbleNumber))
              overlapMap(pubbleNumber) += otherPubbleNumber
            else
              overlapMap += (pubbleNumber -> mutable.ListBuffer(otherPubbleNumber))


            if (overlapMap.contains(otherPubbleNumber))
              overlapMap(otherPubbleNumber) += pubbleNumber
            else
              overlapMap += (otherPubbleNumber -> mutable.ListBuffer(pubbleNumber))
          }
        }
      }
    }

    println(overlapMap)

    var result = List[Int](pointOfWaterStrider)

    addResult(pointOfWaterStrider)

    def addResult(pubbleNumber: Int): Unit = {
      var pubbleNumberForRecall = List[Int]()

      if (overlapMap.contains(pubbleNumber)) {
        overlapMap(pubbleNumber).foreach { overlapPubbleNumber =>
          if (!result.contains(overlapPubbleNumber)) {
            result :+= overlapPubbleNumber
            pubbleNumberForRecall :+= overlapPubbleNumber
          }
        }

        if (!pubbleNumberForRecall.isEmpty)
          pubbleNumberForRecall.foreach(addResult(_))
      }
    }

    result.sorted.foreach(println(_))
  }

  def isOverlap(self: ((Int, Int), (Int, Int)), other: ((Int, Int), (Int, Int))): Boolean = {

    val ((selfStartX, selfStartY), (selfEndX, selfEndY)) = self
    val ((otherStartX, otherStartY), (otherEndX, otherEndY)) = other

    println(self)
    println(other)


    // X軸について
    // self  ------
    // other   ------
    // のような重なり
    val isOverlapX1 = (selfStartX <= otherStartX && otherStartX <= selfEndX && selfEndX <= otherEndX)
    // self    ------
    // other ------
    // のような重なり
    val isOverlapX2 = (otherStartX <= selfStartX && selfStartX <= otherEndX && otherEndX <= selfEndX)


    // Y軸について
    // self  ------
    // other   ------
    // のような重なり
    val isOverlapY1 = (selfStartY <= otherStartY && otherStartY <= selfEndY && selfEndY <= otherEndY)
    // self    ------
    // other ------
    // のような重なり
    val isOverlapY2 = (otherStartY <= selfStartY && selfStartY <= otherEndY && otherEndY <= selfEndY)



    println("isOverlapX1 || isOverlapX2 = " + (isOverlapX1 || isOverlapX2))
    println("isOverlapY1 || isOverlapY2 = " + (isOverlapY1 || isOverlapY2))

    // X軸とY軸両方で重なりがある場合は領域として重なりがあるとみなす
    (isOverlapX1 || isOverlapX2) && (isOverlapY1 || isOverlapY2)
  }


  def exec(lines: List[String]): Unit = {
    val Array(timeA, timeB, timeC) = lines(0).split(" ").map(_.toInt)

    // 遅くてもいつまでにノギ駅についておく必要があるか
    val timeLimitOfCompany = LocalTime.of(8, 59)
    val timeLimitOfNogi = timeLimitOfCompany.minusMinutes(timeC)

    // 遅くてもいつまでにパイザ駅を出発する必要があるか
    val timeLimitOfPaiza = timeLimitOfNogi.minusMinutes(timeB)

    // 上記条件を満たす最遅の発車時刻を求める
    val timeTableRowCount = lines(1).toInt
    val timeTables = lines.tail.tail.map(line => {
      val hourAndMinuts = line.split(" ")
      LocalTime.of(hourAndMinuts(0).toInt, hourAndMinuts(1).toInt)
    })

    // タイムテーブルを遅い方から見て、条件を満たす発車時刻を求める
    val departureTimeOfA = timeTables.reverse.find(_.compareTo(timeLimitOfPaiza) <= 0).get

    // 発車時刻からaを引いた時間 = 求める結果
    val departureTimeOfHome = departureTimeOfA.minusMinutes(timeA)

    // 結果出力
    println(departureTimeOfHome.format(DateTimeFormatter.ofPattern("HH:mm")))

  }


  def hoge(): Unit = {
    val lines = stdin.getLines().toList
    val xyz = lines(0).split(" ").map(_.toInt)
    val Array(candidatesount, electorateCount, speachCount) = xyz

    var votes = Array.fill[Int](candidatesount)(0)
    var noVoters = electorateCount

    lines.tail.foreach(line => {
      val candidateIndex = line.toInt - 1

      if (noVoters > 0) {
        noVoters -= 1
        votes(candidateIndex) += 1
      }

      (0 until votes.length).foreach(i => {
        if (i != candidateIndex && votes(i) > 0) {
          votes(i) -= 1
          votes(candidateIndex) += 1
        }
      })

    })

    val max = votes.reduceLeft(_ max _)

    (0 until votes.length).foreach(i => {
      if (votes(i) == max) println(i + 1)
    })
  }
}
