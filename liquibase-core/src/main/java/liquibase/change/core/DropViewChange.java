package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeClass;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropViewStatement;
import liquibase.util.StringUtils;

/**
 * Drops an existing view.
 */
@ChangeClass(name="dropView", description = "Drop View", priority = ChangeMetaData.PRIORITY_DEFAULT, appliesTo = "view")
public class DropViewChange extends AbstractChange {
    private String schemaName;
    private String viewName;

    @ChangeProperty(mustApplyTo ="view.schema")
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = StringUtils.trimToNull(schemaName);
    }

    @ChangeProperty(requiredForDatabase = "all", mustApplyTo = "view")
    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{
                new DropViewStatement(getSchemaName() == null?database.getDefaultSchemaName():getSchemaName(), getViewName()),
        };
    }

    public String getConfirmationMessage() {
        return "View "+getViewName()+" dropped";
    }

}