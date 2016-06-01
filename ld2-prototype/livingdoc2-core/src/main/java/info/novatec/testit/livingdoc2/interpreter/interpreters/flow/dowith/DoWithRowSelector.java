package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import info.novatec.testit.livingdoc2.api.interpreter.RowSelector;
import info.novatec.testit.livingdoc2.api.interpreter.RowType;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.reflection.ClassPathScanner;
import info.novatec.testit.livingdoc2.reflection.RowTypeStore;
import info.novatec.testit.livingdoc2.reflection.scanner.RowTypeScanner;
import info.novatec.testit.livingdoc2.util.StringUtil;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adapted from LivingDoc 1.0 DoWithRowSelector
 *         <p/>
 *         Instantiates the appropriate {@link RowType}s for given {@link ValueRow} in case of {@link
 *         info.novatec.testit.livingdoc2.interpreter.interpreters.DoWithInterpreter}
 */
public class DoWithRowSelector implements RowSelector {

    private final static int ROWTYPE_KEYWORD_INDEX = 0;

    public DoWithRowSelector() {
    }

    @Override
    public RowType select(ValueRow structureRow) {
        instantiateRowTypeStore();

        ValueCell firstCell = structureRow.getValueCell(ROWTYPE_KEYWORD_INDEX);
        String firstCellContent = StringUtil.convertToUpperCamelcase(firstCell.getContent());

        if (isARowType(firstCellContent)) {
            try {
                Class<? extends RowType> rowTypeClass = getRowTypeClass(firstCellContent);
                return instantiateRowType(structureRow, rowTypeClass);
            } catch (ReflectiveOperationException e) {
                firstCell.setIndicator(ContentModificationIndicator.EXCEPTION);
                firstCell.setResult(new ExceptionResult(null, e));
                return null;
            }
        }
        return new DefaultRow(structureRow);
    }

    /**
     * Check if the {@link RowTypeStore} was already filled.
     * Initiates a scan if not.
     */
    private void instantiateRowTypeStore() {
        if (RowTypeStore.getInstance().isNotFilled()) {
            new ClassPathScanner().scan(new RowTypeScanner());
        }
    }

    private boolean isARowType(String firstRowCellContent) {
        return RowTypeStore.getInstance().isItemAlreadyPresent(firstRowCellContent);
    }

    private Class<? extends RowType> getRowTypeClass(String firstCellContent) throws ClassNotFoundException {
        Class<?> rowTypeClass = RowTypeStore.getInstance().getRowType(firstCellContent);
        return Class.forName(rowTypeClass.getName()).asSubclass(RowType.class);
    }

    private RowType instantiateRowType(ValueRow structureRow, Class<? extends RowType> rowTypeClass)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<? extends RowType> rowTypeConstructor = rowTypeClass.getConstructor(ValueRow.class);
        return rowTypeConstructor.newInstance(structureRow);
    }
}
