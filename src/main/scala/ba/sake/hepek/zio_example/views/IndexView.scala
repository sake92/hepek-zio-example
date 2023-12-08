package ba.sake.hepek.zio_example.views

import Bundle.*, Tags.*

object IndexView extends MyTemplate:
  override def pageSettings =
    super.pageSettings.withTitle("ZIO Hepek")

  override def pageContent = Grid.row(
    s"""
      ## Welcome
      Some content..
      - one
      - two
      - test
    """.md,
    a(href := "showForm")(
      button(Classes.btnClass, Classes.btnPrimary)("Customer form"),
    ),
  )
