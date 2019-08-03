package jdr.tv.ui.adapter

abstract class TypedAdapter<T> : BaseAdapter() {

    var data: T? = null

    fun submitData(data: T) {
        this.data = data
        requestModelBuild()
    }

    override fun buildModels(modelBuilder: ModelBuilder) {
        data?.also { buildModels(modelBuilder, it) }
    }

    abstract fun buildModels(modelBuilder: ModelBuilder, data: T)
}
