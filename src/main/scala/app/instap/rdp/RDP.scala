package app.instap
package ramerdouglaspeucker

import scala.collection.mutable.ListBuffer

final case class Point(key: Double, value: Double) {
  def perpendicularDistance(
      lineStart: Point,
      lineEnd: Point,
    ): Double = {
    var dx: Double = lineEnd.key - lineStart.key
    var dy: Double = lineEnd.value - lineStart.value

    // Normalize
    val mag = Math.hypot(dx, dy)
    if (mag > 0.0) {
      dx /= mag
      dy /= mag
    }
    val pvx = key - lineStart.key
    val pvy = value - lineStart.value

    // Get dot product (project pv onto normalized direction)
    val pvdot = dx * pvx + dy * pvy

    // Scale line direction vector and subtract it from pv
    val ax = pvx - pvdot * dx
    val ay = pvy - pvdot * dy

    Math.hypot(ax, ay)
  }
}

object RDP {
  def ramerDouglasPeucker(
      pointList: List[Point],
      epsilon: Double,
    ): List[Point] = {
    if (pointList.size < 2) throw new IllegalArgumentException("Not enough points to simplify")

    // Find the point with the maximum distance from line between the start and end
    var dmax = 0.0
    var p: Point = null
    // val end = pointList.size - 1
    pointList foreach { point =>
      val d = point.perpendicularDistance(pointList.head, pointList.last);
      if (d > dmax) {
        p = point
        dmax = d
      }
    }

    val index = pointList.indexOf(p)

    val out = ListBuffer[Point]()

    // If max distance is greater than epsilon, recursively simplify
    if (dmax > epsilon) {
      val firstLine = pointList.slice(0, index + 1)
      val lastLine = pointList.slice(index, pointList.size)
      val recResults1 = ramerDouglasPeucker(firstLine, epsilon)
      val recResults2 = ramerDouglasPeucker(lastLine, epsilon)

      // build the result list
      out.addAll(recResults1.slice(0, recResults1.size - 1));
      out.addAll(recResults2);
      if (out.size < 2) throw new RuntimeException("Problem assembling output");
      out.toList
    }
    else
      List(pointList.head, pointList.last)
  }
}
