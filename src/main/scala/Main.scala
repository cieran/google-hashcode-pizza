import scala.io.Source
object Main extends App {

  val input =
    """5 1 2 1
  3 onion pepper olive
  3 mushroom tomato basil
  3 chicken mushroom pepper
  3 tomato mushroom basil
  2 chicken basil""".stripMargin.split("\n").toList

  def setupPizzeria(input: List[String]): Pizzeria = {

    val numOfPizzas: Int = input.head.head.asDigit
    val pizzas: List[Pizza] = input.tail
      .map(pizza => {
        val pizzaInfo = pizza.trim().split(" ").toList
        Pizza(pizzaInfo.head.toInt, pizzaInfo.tail.sorted)
      })
      .sortBy(_.numOfToppings)
      .reverse

    Pizzeria(numOfPizzas, pizzas)

  }

  def setupTeams(input: List[String]): List[Team] = {
    val teamSizes =
      input.head.tail.trim().split(" ").toList.map(_.toInt).zipWithIndex

    teamSizes
      .flatMap(sizeAndIndex => {
        val (people, index) = sizeAndIndex
        Range(0, people).map(_ => Team(index + 2))
      })
      .sortBy(_.size)
      .reverse

  }

  val pizzeria = setupPizzeria(input)
  val teams = setupTeams(input)

  def buildDeliveries(pizzeria: Pizzeria, teams: List[Team]): List[Delivery] = {
    val allPizzas = pizzeria.pizzas
    val allTeams = teams

    buildDeliveryForTeam(List.empty, allTeams, allPizzas)
      .sortBy(_.team.size)
      .reverse

  }

  def buildDeliveryForTeam(
      deliveries: List[Delivery],
      teams: List[Team],
      pizzas: List[Pizza]
  ): List[Delivery] = {
    teams.headOption match {
      case None => deliveries
      case Some(team) => {
        val (pizzasForTeam, remainingPizzas) =
          getNPizzas(team.size, List.empty, pizzas)
        val uniqueToppings =
          pizzasForTeam.flatMap(pizza => pizza.toppings).distinct.length
        val delivery =
          Delivery(team, pizzasForTeam, uniqueToppings * uniqueToppings)
        val newDeliveries = delivery :: deliveries
        val remainingTeams = teams.tail

        buildDeliveryForTeam(newDeliveries, remainingTeams, remainingPizzas)
      }
    }
  }

  private def getNPizzas(
      n: Int,
      teamPizza: List[Pizza],
      sparePizza: List[Pizza]
  ): (List[Pizza], List[Pizza]) = {
    n match {
      case 0 => (teamPizza, sparePizza)
      case _ => {
        sparePizza.headOption match {
          case None => (teamPizza, sparePizza)
          case Some(pizza) => {
            val currTeamPizza = pizza :: teamPizza
            getNPizzas(n - 1, currTeamPizza, sparePizza.tail)
          }
        }

      }
    }
  }

  println(buildDeliveries(pizzeria, teams))
}
