package app.instap
package rdp

final class SimplifyTest extends TestSuite {
  test("Simplify should simplify a to 5 elements") {

    val pointList = List(
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

    val pointListOut = RamerDouglasPeucker(pointList, 1.0).out

    pointListOut shouldBe List(
      Point(0.0, 0.0),
      Point(2.0, -0.1),
      Point(3.0, 5.0),
      Point(7.0, 9.0),
      Point(9.0, 9.0),
    )
  }
}
