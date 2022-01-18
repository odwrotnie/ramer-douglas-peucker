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
final class SimplifyByDistanceTest extends TestSuite {
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

  // test("Random limit by distance") {
  //   for {
  //     limit <- List(0.01, 0.02, 0.03, 0.1, 0.2, 0.3, 200,  300, 500, 1000)
  //     count <- (3 to 6).map(Math.pow(10, _).toInt)
  //   } yield {
  //     val pointList = (1 to count).map(_ => new Point(Random.nextDouble, Random.nextDouble))
  //     val c = RamerDouglasPeucker(pointList).simplifiedByGreatestDistance(limit).size
  //     println(s"Random limit: $limit, count: $count = $c")
  //     // (limit to limit + 2) should contain(c)
  //   }
  //   1 shouldBe(1)
  // }
}
