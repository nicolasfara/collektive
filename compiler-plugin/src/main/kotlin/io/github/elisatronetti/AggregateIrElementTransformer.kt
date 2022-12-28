package io.github.elisatronetti

import io.github.elisatronetti.utils.common.Name
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

/**
 * Looking for the aggregate function call, which is the one that contains the function calls
 * and the branches that have to be aligned.
 */
class AggregateIrElementTransformer(
    private val pluginContext: IrPluginContext,
    private val aggregateContextClass: IrClass,
    private val alignedOnFunction: IrFunction
) : IrElementTransformerVoid() {

    override fun visitCall(expression: IrCall): IrExpression {
        if (expression.symbol.owner.name.asString() == Name.AGGREGATE_FUNCTION) {
            val aggregateLambdaBody =
                (expression.getValueArgument(expression.valueArgumentsCount - 1) as IrFunctionExpression).function
            expression.transform(
                AlignmentIrElementTransformer(
                    pluginContext,
                    alignedOnFunction,
                    aggregateLambdaBody,
                    aggregateContextClass
                ),
                null
            )
        }
        return super.visitCall(expression)
    }
}
