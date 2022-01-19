package app.instap
package rdp

import scala.annotation.tailrec

final case class RamerDouglasPeucker(pointList: Seq[Point]) {
  // Find the point with the maximum distance from line between the start and end
  lazy val (farthestPoint, maxDistance) =
    (pointList.headOption, pointList.lastOption) match {
      case (Some(head), Some(last)) =>
        // Find the point with the maximum distance from line between the start and end
        pointList.tail.dropRight(1).foldLeft((pointList.tail.head, 0.0)) {
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
  def simplifiedByCount(count: Double): Seq[Point] =
    if (pointList.size <= count) pointList
    else if (count < 3) List(pointList.headOption.get, pointList.lastOption.get)
    else {
      val (firstLine1, lastLine) = pointList.splitAt(pointList.indexOf(farthestPoint))
      val firstLine = firstLine1.appended(farthestPoint)
      val (s1, s2) = (firstLine.size - 2, lastLine.size - 2)
      val (c1, c2) =
        if (s1 >= s2)
          (math.ceil(s1 * (count - 3) / (s1 + s2)), math.floor(s2 * (count - 3) / (s1 + s2)))
        else (math.floor(s1 * (count - 3) / (s1 + s2)), math.ceil(s2 * (count - 3) / (s1 + s2)))
      println(
        s"size=${pointList.size} count: $count, far=$farthestPoint, max=$maxDistance, size1=${firstLine.size}, size2=${lastLine.size}, c1=$c1, c2=$c2"
      )
      val a: Seq[Point] = RamerDouglasPeucker(firstLine).simplifiedByCount(c1 + 2)
      val b: Seq[Point] = RamerDouglasPeucker(lastLine).simplifiedByCount(c2 + 2)
      a.dropRight(1) ++ b
    }
}
