case class Pizzeria(numOfPizzas: Int, pizzas: List[Pizza]) {

  override def toString(): String = {
    s"""There are $numOfPizzas in this pizzeria. \n$pizzas""".stripMargin
  }
}

case class Pizza(numOfToppings: Int, toppings: List[String]) {
  override def toString(): String = {
    s"""\nPizza with ${numOfToppings} toppings - ${toppings}"""
  }
}
