package pk.gov.pbs.geomap_project;

import android.util.Log;

import com.google.gson.GsonBuilder;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import pk.gov.pbs.database.IDatabaseOperation;
import pk.gov.pbs.database.ModelBasedDatabaseHelper;
import pk.gov.pbs.geomap_project.db.Repository;
import pk.gov.pbs.geomap_project.models.LoginPayload;
import pk.gov.pbs.geomap_project.models.export.PCD_FO;
import pk.gov.pbs.utils.SystemUtils;

public class DebugHelper {
    private static final String TAG = "DebugHelper";
    public static GeoPoint getTestGeoPoint(){
        return new GeoPoint(33.679398679965736, 73.03464969598426);
    }

    public static String getGeoJsonISB(){
        return "{\"type\": \"FeatureCollection\",\"features\": [{\"type\": \"Feature\",\"properties\": {\"id\": 1,\"desc\": \"Sector F11\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[72.97445297241211,33.68578204940791],[72.98466682434082,33.6719256448053],[73.00080299377442,33.680711150104247],[72.99041748046875,33.69420907950361],[72.97445297241211,33.68578204940791]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 2,\"desc\": \"Sector F10\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[72.99196243286133,33.69485178517179],[73.00200462341309,33.681425379371479],[73.0177116394043,33.68942434176882],[73.01788330078125,33.6904955748537],[73.00792694091797,33.70334933033437],[72.99196243286133,33.69485178517179]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 3,\"desc\": \"Fatima Jinah Park (F9)\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.00809860229492,33.70342073471579],[73.01788330078125,33.690424159730117],[73.01942825317383,33.6904955748537],[73.03607940673828,33.69949340559815],[73.026123046875,33.712774195477468],[73.00809860229492,33.70342073471579]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 5,\"desc\": \"Sector F8\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.02689552307129,33.71313117761419],[73.03633689880371,33.69949340559815],[73.05376052856446,33.70863309423801],[73.04380416870116,33.72205524868729],[73.02689552307129,33.71313117761419]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 6,\"desc\": \"Sector F7\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.04431915283203,33.72248358076708],[73.05384635925293,33.708918693837869],[73.07144165039061,33.717985987331157],[73.06131362915039,33.73126391736321],[73.04431915283203,33.72248358076708]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 7,\"desc\": \"Sector F6\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.06217193603516,33.731763584299638],[73.07195663452149,33.718414339715177],[73.08826446533203,33.72698093855922],[73.08354377746582,33.7331911880856],[73.08320045471192,33.734476011184899],[73.08242797851563,33.73554668240412],[73.0821704864502,33.736760093633908],[73.08225631713867,33.7374738569288],[73.08302879333496,33.738687240901487],[73.08345794677735,33.73997198169568],[73.08345794677735,33.74097121123314],[73.08277130126953,33.74239866180931],[73.06217193603516,33.731763584299638]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 10,\"desc\": \"Sector G11\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[72.98569679260254,33.6711399054834],[72.99505233764649,33.65806700735442],[73.01256179809569,33.667211101197548],[73.00294876098633,33.679996914903529],[72.98569679260254,33.6711399054834]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 11,\"desc\": \"Sector G10\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.00312042236328,33.68028260969583],[73.01273345947266,33.667353969952149],[73.02878379821778,33.67585423373371],[73.0191707611084,33.68878159550887],[73.00312042236328,33.68028260969583]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 12,\"desc\": \"Sector G9\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.0195140838623,33.68885301199737],[73.02886962890625,33.67578280644601],[73.04637908935547,33.684853597257838],[73.04637908935547,33.68578204940791],[73.03719520568848,33.698136650178508],[73.0195140838623,33.68885301199737]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 13,\"desc\": \"Sector G8\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.0374526977539,33.69842228467894],[73.04715156555176,33.68521069542541],[73.06139945983887,33.69228093365666],[73.06268692016602,33.696779873333479],[73.0547046661377,33.70756208727991],[73.0374526977539,33.69842228467894]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 14,\"desc\": \"Sector G7\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.05487632751465,33.70784769044128],[73.06397438049317,33.69578012931697],[73.06577682495117,33.69628000277926],[73.06654930114746,33.69628000277926],[73.06740760803223,33.6959943611569],[73.08234214782715,33.70342073471579],[73.07204246520996,33.71698649012371],[73.05487632751465,33.70784769044128]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 15,\"desc\": \"Sector G6\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.07255744934082,33.71712927615144],[73.0825138092041,33.703492139037859],[73.09916496276856,33.7124172118567],[73.08929443359375,33.72576738903661],[73.07255744934082,33.71712927615144]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 18,\"desc\": \"Shakar Pariyan\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.06577682495117,33.69435190340085],[73.08740615844727,33.66435367627463],[73.09289932250977,33.67149706061054],[73.09616088867188,33.67835415142453],[73.10457229614258,33.68221102471215],[73.10989379882811,33.68878159550887],[73.11040878295899,33.69835087614285],[73.10199737548827,33.71320257386344],[73.06577682495117,33.69435190340085]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 20,\"desc\": \"Sector H10\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.01273345947266,33.66406792856204],[73.02337646484375,33.65049381900442],[73.04054260253906,33.65992448007282],[73.03041458129883,33.674497105121798],[73.01273345947266,33.66406792856204]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 21,\"desc\": \"Sector H9\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.0312728881836,33.67463996177377],[73.04174423217774,33.65992448007282],[73.05976867675781,33.66878264444685],[73.048095703125,33.68321092658007],[73.0312728881836,33.67463996177377]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 22,\"desc\": \"Sector H8\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.04912567138672,33.68321092658007],[73.06062698364258,33.66978270247389],[73.0755615234375,33.67792559926798],[73.06577682495117,33.69420907950361],[73.06217193603516,33.69235234723729],[73.06217193603516,33.689495757723218],[73.04912567138672,33.68321092658007]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 30,\"desc\": \"Sector I10\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.02389144897461,33.65020802526355],[73.03573608398438,33.63720340625724],[73.05204391479492,33.645921005279948],[73.04191589355469,33.65906718995458],[73.02389144897461,33.65020802526355]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 31,\"desc\": \"Sector I9\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.0426025390625,33.6594958360814],[73.05204391479492,33.64634971688594],[73.06955337524414,33.65535216740787],[73.06028366088867,33.66806831016572],[73.0426025390625,33.6594958360814]]]}},{\"type\": \"Feature\",\"properties\": {\"id\": 32,\"desc\": \"Sector I8\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.06131362915039,33.6689255105912],[73.0697250366211,33.65606660727673],[73.08671951293946,33.66421080253697],[73.07676315307617,33.677068488547018],[73.06131362915039,33.6689255105912]]]}}]}";
    }

    public static LoginPayload getLoginPayload(){
        return new LoginPayload(
                "0123",
                "Test User",
                1,
                SystemUtils.getUnixTs() + 987898,
                null,
                "empty token"
        );
    }

    public static String getBlockBoundary(String blockCode){
        switch (blockCode){
            case "01234567":
                return "{\"type\": \"FeatureCollection\",\"features\": [{\"type\": \"Feature\",\"properties\": {\"ebcode\":\"01234567\",\"id\": 12,\"desc\": \"Sector G9\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.0195140838623,33.68885301199737],[73.02886962890625,33.67578280644601],[73.04637908935547,33.684853597257838],[73.04637908935547,33.68578204940791],[73.03719520568848,33.698136650178508],[73.0195140838623,33.68885301199737]]]}}]}";
            case "098765432":
                return "{\"type\": \"FeatureCollection\",\"features\": [{\"type\": \"Feature\",\"properties\": {\"ebcode\":\"098765432\",\"id\": 11,\"desc\": \"Sector G10\"},\"geometry\": {\"type\": \"Polygon\",\"coordinates\": [[[73.00312042236328,33.68028260969583],[73.01273345947266,33.667353969952149],[73.02878379821778,33.67585423373371],[73.0191707611084,33.68878159550887],[73.00312042236328,33.68028260969583]]]}}]}";
            default:
                return null;
        }
    }

    public static void createDummyBlock(Repository repository){
        repository.executeDatabaseOperation(new IDatabaseOperation<ArrayList<Long>>() {
            @Override
            public ArrayList<Long> execute(ModelBasedDatabaseHelper db) {
                String json = "[{\"mAltitude\":0.0,\"mLatitude\":33.682854261312556,\"mLongitude\":73.03661625556555},{\"mAltitude\":0.0,\"mLatitude\":33.68454355669036,\"mLongitude\":73.03771508417314},{\"mAltitude\":0.0,\"mLatitude\":33.68678036059388,\"mLongitude\":73.03826808929443},{\"mAltitude\":0.0,\"mLatitude\":33.69206514329447,\"mLongitude\":73.03805351257324},{\"mAltitude\":0.0,\"mLatitude\":33.6918151946774,\"mLongitude\":73.03221702575684},{\"mAltitude\":0.0,\"mLatitude\":33.691850901667195,\"mLongitude\":73.02762508392334},{\"mAltitude\":0.0,\"mLatitude\":33.68849437977782,\"mLongitude\":73.02436351776123},{\"mAltitude\":0.0,\"mLatitude\":33.68749453940772,\"mLongitude\":73.0272388458252},{\"mAltitude\":0.0,\"mLatitude\":33.6848877580389,\"mLongitude\":73.02938461303711},{\"mAltitude\":0.0,\"mLatitude\":33.680423907734166,\"mLongitude\":73.02831172943115},{\"mAltitude\":0.0,\"mLatitude\":33.678745440060965,\"mLongitude\":73.02260398864746},{\"mAltitude\":0.0,\"mLatitude\":33.683209377515446,\"mLongitude\":73.0189561843872},{\"mAltitude\":0.0,\"mLatitude\":33.68210234263973,\"mLongitude\":73.01556587219238},{\"mAltitude\":0.0,\"mLatitude\":33.684102137192504,\"mLongitude\":73.01359176635742},{\"mAltitude\":0.0,\"mLatitude\":33.680423907734166,\"mLongitude\":73.01264762878418},{\"mAltitude\":0.0,\"mLatitude\":33.67474555523327,\"mLongitude\":73.01178932189941},{\"mAltitude\":0.0,\"mLatitude\":33.67188838072805,\"mLongitude\":73.01157474517822},{\"mAltitude\":0.0,\"mLatitude\":33.6711026410976,\"mLongitude\":73.01552295684814},{\"mAltitude\":0.0,\"mLatitude\":33.67917398811383,\"mLongitude\":73.00814151763916},{\"mAltitude\":0.0,\"mLatitude\":33.680852447420904,\"mLongitude\":73.01286220550537},{\"mAltitude\":0.0,\"mLatitude\":33.678459740172386,\"mLongitude\":73.016037940979},{\"mAltitude\":0.0,\"mLatitude\":33.678031188559515,\"mLongitude\":73.01612377166748}]";
                GeoPoint[] geoPoints = new GsonBuilder().create().fromJson(json, GeoPoint[].class);
                ArrayList<Long> result = new ArrayList<>();
                PCD_FO obj = new PCD_FO(
                        "Mouza",
                        "Province",
                        "District",
                        1,
                        "Tehsil",
                        "Field Office",
                        "true",
                        "Dummy Name",
                        "Dummy Name",
                        "01234567890",
                        "Locality",
                        "Test Address",
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                );
                obj.PCode = "1234567";
                obj.EBCode = "01234567";
                obj.Quarter = 1;
                obj.Assignee = "01234567891012";
                obj.Assigner = "98765432101023";
                obj.DBegin = "31/05/2021";
                obj.DEnd = "08/07/2021";
                obj.DAssigned = "08/07/2020";

                for (int i = 0; i < 10; i++){
                    obj.HHNo = i+1;
                    obj.Longitude = geoPoints[i].getLongitude();
                    obj.Latitude = geoPoints[i].getLatitude();

                    result.add(repository.getDatabase().insert(obj));
                }

                obj.PCode = "9876543";
                obj.EBCode = "098765432";
                obj.DBegin = "31/06/2021";
                obj.DEnd = "08/09/2021";
                obj.DAssigned = "08/07/2020";

                for (int i = 10; i <= 20; i++){
                    obj.HHNo = i;
                    obj.Longitude = geoPoints[i].getLongitude();
                    obj.Latitude = geoPoints[i].getLatitude();

                    result.add(repository.getDatabase().insert(obj));
                }
                return result;
            }

            @Override
            public void postExecute(ArrayList<Long> result) {
                if (result.size() > 0)
                    Log.d(TAG, "postExecute: Insertion Completed! " + result.size() + " Entries added");
                else
                    Log.d(TAG, "postExecute: Insertion failed!");

            }
        });
    }
}
