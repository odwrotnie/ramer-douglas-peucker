// package app.instap
// package rdp

// import plotly._
// import plotly.element._
// import plotly.layout._
// import plotly.Almond._

// final class SimplifyAndDrawTest extends TestSuite {
//   test("Drwa plot") {
//     val x = (0 to 100).map(_ * 0.1)
//     val y1 = x.map(d => 2.0 * d + util.Random.nextGaussian())
//     val y2 = x.map(math.exp)

//     val plot = Seq(
//       Scatter(x, y1).withName("Approx twice"),
//       Scatter(x, y2).withName("Exp"),
//     )

//     val lay = Layout().withTitle("Curves")
//     plot.plot("plot") // attaches to div element with id 'plot
//   }
// }
