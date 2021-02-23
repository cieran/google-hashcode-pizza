case class Team(size: Int) {

  override def toString(): String =
    s"""\nTeam of size $size""".stripMargin
}
