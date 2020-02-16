package jdr.tv.common.log

private const val DEBUG = 3
private const val INFO = 4
private const val WARN = 5
private const val ERROR = 6

sealed class Level(val value: Int, val symbol: String)

object Debug : Level(DEBUG, "DEBUG")
object Info : Level(INFO, "INFO")
object Warn : Level(WARN, "WARN")
object Error : Level(ERROR, "ERROR")
