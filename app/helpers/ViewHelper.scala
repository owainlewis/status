package helpers

object ViewHelper {

  def pluralize(n: Int, s: String) =
    if (n == 1) s else s ++ "s"
}
