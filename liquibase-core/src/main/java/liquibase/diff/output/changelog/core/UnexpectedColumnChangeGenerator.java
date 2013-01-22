package liquibase.diff.output.changelog.core;

import liquibase.change.Change;
import liquibase.change.core.DropColumnChange;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.*;

public class UnexpectedColumnChangeGenerator implements UnexpectedObjectChangeGenerator {
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Column.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NONE;
    }

    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[] {
                PrimaryKey.class,
                ForeignKey.class,
                Table.class,
        };
    }

    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return null;
    }

    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Column column = (Column) unexpectedObject;
//        if (!shouldModifyColumn(column)) {
//            continue;
//        }
        if (column.getRelation() instanceof View) {
            return null;
        }

        DropColumnChange change = new DropColumnChange();
        change.setTableName(column.getRelation().getName());
        if (control.isIncludeCatalog()) {
            change.setCatalogName(column.getRelation().getSchema().getCatalogName());
        }
        if (control.isIncludeSchema()) {
            change.setSchemaName(column.getRelation().getSchema().getName());
        }
        change.setColumnName(column.getName());

        return new Change[] { change };

    }
}