package filters

import play.api.http.HttpFilters
import play.filters.csrf.CSRFFilter
import javax.inject.Inject

class MyFilters @Inject() (csrfFilter: CSRFFilter) extends HttpFilters {
  def filters = Seq(csrfFilter)
}
