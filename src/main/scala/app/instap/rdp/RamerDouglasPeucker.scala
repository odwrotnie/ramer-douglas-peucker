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
  def simplifiedByCount(count: Double): Seq[Point] = {
    (pointList.headOption, pointList.lastOption) match {
      case (Some(head), Some(last)) =>
        // If size greater than count, recursively simplify
        if (count > 2 && pointList.size > count - 2) {
          val (firstLine, lastLine) = pointList.splitAt(pointList.indexOf(farthestPoint))
          val a: Seq[Point] = RamerDouglasPeucker(firstLine).simplifiedByCount(
            (count * firstLine.size / pointList.size) - 1
          )
          val b: Seq[Point] = RamerDouglasPeucker(lastLine).simplifiedByCount(
            (count * lastLine.size / pointList.size) // - 1
          )
          a.dropRight(1) ++ b
        }
        else List(head, last)
      case _ => pointList
    }
  }
}
