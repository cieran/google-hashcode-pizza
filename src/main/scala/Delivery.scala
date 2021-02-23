case class Delivery(team: Team, pizzas: List[Pizza], score: Int) {

  override def toString(): String =
    s"\nTeam Size: ${team.size}\nScore: $score\nPizzas: ${pizzas.length}\n"

}
