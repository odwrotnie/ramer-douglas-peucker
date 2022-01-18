package app.instap
package rdp

import scala.annotation.tailrec

final case class RamerDouglasPeucker(pointList: Seq[Point]) {
  // Find the point with the maximum distance from line between the start and end
  lazy val (farthestPoint, maxDistance) =
    (pointList.headOption, pointList.lastOption) match {
      case (Some(head), Some(last)) =>
        // Find the point with the maximum distance from line between the start and end
        pointList.foldLeft((head, 0.0)) {
          case (acc @ (_, maxDistance), point) =>
            val distance = point.perpendicularDistance(head, last)
            if (distance > maxDistance) (point, distance) else acc
        }
    }

  // @tailrec
  def simplifiedByGreatestDistance(epsilon: Double): Seq[Point] =
    (pointList.headOption, pointList.lastOption) match {
      case (Some(head), Some(last)) =>
        // If max distance is greater than epsilon, recursively simplify
        if (maxDistance > epsilon) {
          val (firstLine, lastLine) = pointList.splitAt(pointList.indexOf(farthestPoint))
          val a: Seq[Point] =
            RamerDouglasPeucker(firstLine).simplifiedByGreatestDistance(epsilon)
          val b: Seq[Point] = RamerDouglasPeucker(lastLine).simplifiedByGreatestDistance(epsilon)
          a.dropRight(1) ++ b
        }
        else List(head, last)
      case _ => pointList
    }

  // @tailrec
  def simplifiedByCount(count: Int): Seq[Point] =
    // println(s"Simplify $pointList by count: $count")
    (pointList.headOption, pointList.lastOption, count) match {
      case _ if count == 0 => Seq.empty
      case _ if count == pointList.size => pointList
      case (Some(head), _, 1) => farthestPoint :: Nil
      case (Some(head), Some(tail), 2) => head :: tail :: Nil
      case _ if pointList.size <= 2 => pointList
      case (Some(head), Some(last), 3) => head :: farthestPoint :: last :: Nil
      case _ =>
        val (leftLine, rightLine) = pointList.splitAt(pointList.indexOf(farthestPoint)) // Only right side has the farthest point
        // println("Left: " + leftLine)
        // println("Right: " + rightLine)
        val leftCount = Math.round(count * leftLine.size / pointList.size).toInt // Neceessary?
        val rightCount = count - leftCount
        // println(
        //   s"Simplifying ${pointList.size} points to $leftCount + $rightCount, max: $farthestPoint"
        // )
        val a: Seq[Point] = RamerDouglasPeucker(leftLine).simplifiedByCount(leftCount)
        val b: Seq[Point] = RamerDouglasPeucker(rightLine).simplifiedByCount(rightCount)
        // println(s"A: $a")
        // println(s"B: $b")
        a ++ b
    }
}
