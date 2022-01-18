package app.instap
package rdp

import scala.util.Random

@SuppressWarnings(
  Array(
    "org.wartremover.warts.StringPlusAny",
    "org.wartremover.warts.NonUnitStatements",
    "org.wartremover.warts.Any",
    "org.wartremover.warts.Nothing",
  )
)
final class SimplifyByCountTest extends TestSuite {
  test("Small 2/3") {
    val pointList = List(
      new Point(0, 0),
      new Point(1, 1),
    )
    RamerDouglasPeucker(pointList).simplifiedByCount(3) shouldBe pointList
  }

  test("Small 3/3") {
    val pointList = List(
      new Point(0, 1),
      new Point(1, 9),
      new Point(2, 1),
    )
    RamerDouglasPeucker(pointList).simplifiedByCount(3) shouldBe pointList
  }

  test("Small 3/2") {
    val pointList = List(
      new Point(0, 1),
      new Point(1, 9),
      new Point(2, 1),
    )
    RamerDouglasPeucker(pointList).simplifiedByCount(2) shouldBe List(
      new Point(0, 1),
      new Point(2, 1),
    )
  }

  test("Small 6/4 middle") {
    val pointList = List(
      new Point(0, 1),
      new Point(1, 1),
      new Point(2, 1),
      new Point(3, 2),
      new Point(4, 1),
      new Point(5, 1),
    )
    RamerDouglasPeucker(pointList).simplifiedByCount(4).size shouldBe 4
  }

  test("Small 6/4 left") {
    val pointList = List(
      new Point(0, 1),
      new Point(1, 2),
      new Point(2, 1),
      new Point(3, 1),
      new Point(4, 1),
      new Point(5, 1),
    )
    RamerDouglasPeucker(pointList).simplifiedByCount(4).size shouldBe 4
  }

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

  test("Random limit by count") {
    for {
      limit <- List(10, 20, 30, 50, 100, 150, 200, 300, 500, 1000)
      count <- (3 to 5).map(Math.pow(10, _).toInt)
    } yield {
      val pointList = (1 to count).map(_ => new Point(Random.nextDouble(), Random.nextDouble()))
      RamerDouglasPeucker(pointList).simplifiedByCount(limit).size shouldBe limit
    }
  }
}
