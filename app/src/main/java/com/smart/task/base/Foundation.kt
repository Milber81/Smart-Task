package com.smart.task.base

/**
 * A functional interface representing a generic mapper for transforming a list of models into a list of views.
 *
 * This interface allows defining transformation logic between two types (`Model` and `View`),
 * used for converting domain models to UI models or vice versa.
 *
 * @param Model The input type (data layer representation).
 * @param View The output type (presentation layer representation).
 */
fun interface ListMapper<in Model : Any, out View : Any> {
    /**
     * Transforms a list of items from type [Model] to a list of items of type [View].
     *
     * @param items The list of input models to be transformed.
     * @return A list of transformed view models.
     */
    suspend fun map(items: List<Model>): List<View>
}

/**
 * A functional interface for mapping a single DTO (Data Transfer Object) to a Model.
 * This can be used for transforming data between layers or different representations.
 *
 * @param Dto the type of the input data (Data Transfer Object)
 * @param Model the type of the output model after transformation
 */
fun interface SingleMapper<in Dto: Any, out Model: Any> {

    /**
     * Transforms a given DTO to the corresponding Model.
     * This method should define the logic for mapping one type to another.
     *
     * @param item the input data of type [Dto] to be mapped
     * @return the transformed data of type [Model]
     */
    fun map(item: Dto): Model
}
