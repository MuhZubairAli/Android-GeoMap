package pk.gov.pbs.geomap_project.db;

import android.app.Application;

import pk.gov.pbs.database.ModelBasedDatabaseHelper;
import pk.gov.pbs.database.ModelBasedRepository;

public class Repository extends ModelBasedRepository {
    protected final Database mFormBuilderDatabase;

    public Repository(Application app){
        super(app);
        mFormBuilderDatabase = Database.getInstance(app);
    }

    public ModelBasedDatabaseHelper getDatabase() {
        return mFormBuilderDatabase;
    }

}
