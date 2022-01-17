package app.instap
package rdp

import scala.collection.mutable.ListBuffer

final case class RamerDouglasPeucker(pointList: List[Point], epsilon: Double) {
  require(pointList.size >= 2, "Not enough points to simplify")

  // Find the point with the maximum distance from line between the start and end
  val pointToDistanceMap: Map[Point, Double] = pointList map { point =>
    point -> point.perpendicularDistance(pointList.head, pointList.last);
  } toMap

  val (farthestPoint, maxDistance) = pointToDistanceMap.maxBy(_._2)

  private val index = pointList.indexOf(farthestPoint)

  // If max distance is greater than epsilon, recursively simplify
  lazy val out: List[Point] = if (maxDistance > epsilon) {

    val out = ListBuffer[Point]()

    val firstLine = pointList.slice(0, index + 1)
    val lastLine = pointList.slice(index, pointList.size)
    val recResults1: List[Point] = RamerDouglasPeucker(firstLine, epsilon).out
    val recResults2: List[Point] = RamerDouglasPeucker(lastLine, epsilon).out

    // build the result list
    out.addAll(recResults1.slice(0, recResults1.size - 1));
    out.addAll(recResults2);
    if (out.size < 2) throw new RuntimeException("Problem assembling output");
    out.toList
  }
  else
    List(pointList.head, pointList.last)
}
