package com.nvkhang96.news.data

import androidx.navigation.NavDestination
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class VnexpressRssResponseWrapper @JvmOverloads constructor(
    @field: Element(name = "channel")
    var channel: VnexpressRssResponse? = null
)

@Root(name = "channel", strict = false)
class VnexpressRssResponse(
    @field: ElementList(inline = true)
    var itemList: List<VnexpressRssItem>? = null
)

@Root(name = "item", strict = false)
data class VnexpressRssItem @JvmOverloads constructor(
    @field: Element(name = "title")
    var title: String = "",
    @field: Element(name = "description", required = false)
    var destination: String = "",
    @field: Element(name = "link")
    var link: String = "",
    @field: Element(name = "pubDate")
    var pubDate: String = ""
)

