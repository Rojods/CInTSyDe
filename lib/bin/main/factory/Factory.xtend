package factory

import forsyde.io.java.core.ForSyDeSystemGraph
import strategy.Strategy

interface Factory {
	def void create()
	def void add(Strategy strategy)
}