package ba.sake.hepek.zio_example.views

import Bundle.*, Tags.*, Classes.*

trait MyTemplate extends Page {
  override def bodyContent =
    div(clsContainer)(
      Navbar.nav(
        brandUrl = "/",
        brandName = Some("Hepek - ZIO"),
        brandIconUrl = Some("/assets/zio.png"),
        left = List(
          Navbar.link("showForm", "Form"),
          Navbar.dropdown(
            "Nested",
            List(
              Navbar.dropdownLink("#", "Nested1"),
              Navbar.dropdownLink("#", "Nested2"),
            ),
          ),
        ),
        right = List(Navbar.link("#", "Contact us")),
      ),
      pageContent,
    )

  override def styleURLs = super.styleURLs ++
    List("/assets/main.css")
}
