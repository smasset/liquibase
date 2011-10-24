package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeClass;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropSequenceStatement;
import liquibase.util.StringUtils;

/**
 * Drops an existing sequence.
 */
@ChangeClass(name="dropSequence", description = "Drop Sequence", priority = ChangeMetaData.PRIORITY_DEFAULT, appliesTo = "sequence")
public class DropSequenceChange extends AbstractChange {

    private String schemaName;
    private String sequenceName;

    @ChangeProperty(mustApplyTo ="sequence.schema")
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = StringUtils.trimToNull(schemaName);
    }

    @ChangeProperty(requiredForDatabase = "all", mustApplyTo = "sequence")
    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropSequenceStatement(getSchemaName() == null?database.getDefaultSchemaName():getSchemaName(), getSequenceName())};
    }

    public String getConfirmationMessage() {
        return "Sequence " + getSequenceName() + " dropped";
    }
}