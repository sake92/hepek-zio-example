package ba.sake.hepek.zio_example.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class CreateCustomer(
    email: String,
    password: String,
    dob: LocalDate,
    favoriteSuperHero: String,
    // animals: List[String]
  )

object CreateCustomer:
  val empty = CreateCustomer("", "", LocalDate.now, "")
