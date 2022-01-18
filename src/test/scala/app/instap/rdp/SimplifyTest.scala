package app.instap
package rdp

import scala.util.Random

final class SimplifyTest extends TestSuite {
  private val pointList = List(
    new Point(0.0, 0.0),
    new Point(1.0, 0.1),
    new Point(2.0, -0.1),
    new Point(3.0, 5.0),
    new Point(4.0, 6.0),
    new Point(5.0, 7.0),
    new Point(6.0, 8.1),
    new Point(7.0, 9.0),
    new Point(8.0, 9.0),
    new Point(9.0, 9.0),
  )

  test("Simplify by distance") {
    val pointListOut = RamerDouglasPeucker(pointList).simplifiedByGreatestDistance(1.0)
    pointListOut shouldBe List(
      Point(0.0, 0.0),
      Point(2.0, -0.1),
      Point(3.0, 5.0),
      Point(7.0, 9.0),
      Point(9.0, 9.0),
    )
  }

  test("Simplify by 3") {
    val count = RamerDouglasPeucker(pointList).simplifiedByCount(3).size
    List(3, 4) should contain(count)
  }

  test("Simplify by 4") {
    val count = RamerDouglasPeucker(pointList).simplifiedByCount(4).size
    List(4, 5) should contain(count)
  }

  test("Simplify by 5") {
    val count = RamerDouglasPeucker(pointList).simplifiedByCount(5).size
    List(5, 6) should contain(count)
  }

  test("Random") {

    for {
      limit <- List(10, 20, 50, 100, 200, 500, 1000)
      count <- List(10000, 100000)
    } yield {
      println(s"Random limit: $limit, count: $count")
      val pointList = (1 to count).map(_ => new Point(Random.nextDouble, Random.nextDouble))
      (limit to limit + 2) should contain(
        RamerDouglasPeucker(pointList).simplifiedByCount(limit).size
      )
    }
  }
}
