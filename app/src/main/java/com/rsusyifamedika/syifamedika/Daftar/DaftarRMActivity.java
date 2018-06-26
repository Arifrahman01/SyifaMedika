package com.rsusyifamedika.syifamedika.Daftar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsusyifamedika.syifamedika.R;

import java.util.ArrayList;
import java.util.List;

import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionDesaAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionKabAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionKecAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionProvAdapter;
import meridianid.farizdotid.actdaerahindonesia.util.JsonParse;

public class DaftarRMActivity extends AppCompatActivity {
    private Spinner mspAgama, mspStatus, mspPTerakhir, mspProvinsi, mspTanggungan;
    private JsonParse jsonParse;
    private Button mbtDaftarRM;
    private AutoCompleteTextView acttext_prov, acttext_kab, acttext_kec, acttext_desa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_rm);

        mspAgama = findViewById(R.id.spAgama);
        mspStatus = findViewById(R.id.spStatus);
        mspPTerakhir = findViewById(R.id.spPTerakhir);
        mspProvinsi = findViewById(R.id.spProvinsi);
        mspTanggungan = findViewById(R.id.spTanggungan);

        mbtDaftarRM = findViewById(R.id.btDaftarRM);

        acttext_prov = (AutoCompleteTextView) findViewById(R.id.acttext_prov);
        acttext_kab = (AutoCompleteTextView) findViewById(R.id.acttext_kab);
        acttext_kec = (AutoCompleteTextView) findViewById(R.id.acttext_kec);
        acttext_desa = (AutoCompleteTextView) findViewById(R.id.acttext_desa);


        jsonParse = new JsonParse(this);
        initComponents();
        initAdapterACT();

/*        Adapter TAnggungan*/
        List<String> spTanggungan = new ArrayList<String>();
        spTanggungan.add("Umum");
        spTanggungan.add("BPJS");
        spTanggungan.add("Asuransi");
        spTanggungan.add("PT. PLN(Persero)");
        spTanggungan.add("Astra Buana");
        spTanggungan.add("PT. FIF");
        spTanggungan.add("PT. Angkasa Pura ");
        spTanggungan.add("AirNav");
        spTanggungan.add("PT. Hexindo");
        spTanggungan.add("Medilum");
        spTanggungan.add("PT Astra International(ISUZU)");
        spTanggungan.add("Asuransi Takaful");
        spTanggungan.add("PT. JAPFA Comfeed, Tbk");
        spTanggungan.add("Asuransi Wannartha");
        spTanggungan.add("PT. CJ-Cheiljedang Bati-bati");
        spTanggungan.add("PT Ciomas 1");
        spTanggungan.add("PT Ciomas 2");
        spTanggungan.add("PT United Tractor");
        spTanggungan.add("PT Bank Muamalat");
        spTanggungan.add("PT Bank Permata");
        spTanggungan.add("PT Surveyor CCI");
        spTanggungan.add("Avrist Asurance");
        spTanggungan.add("Asuransi Sinarmas");
        spTanggungan.add("Asuransi Allianz");
        spTanggungan.add("Rumah Sakit");
        spTanggungan.add("PT CPU");
        spTanggungan.add("SynTech");
        spTanggungan.add("PT Fullerton Health Indonesia");
        spTanggungan.add("BNI Life");
        spTanggungan.add("Allianz Admedika");
        ArrayAdapter<String> AdapterTanggungan = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, spTanggungan);
        AdapterTanggungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspTanggungan.setAdapter(AdapterTanggungan);


        /*        Adapter Agama*/
        List<String> spAgama = new ArrayList<String>();
        spAgama.add("Islam");
        spAgama.add("Katholik");
        spAgama.add("Protestan");
        spAgama.add("Budha");
        spAgama.add("Hindu");
        spAgama.add("Lainya");
        ArrayAdapter<String> AdapterAgama = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, spAgama);
        AdapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspAgama.setAdapter(AdapterAgama);

        /*     Adapter Status*/
        List<String> spStatus = new ArrayList<String>();
        spStatus.add("Belum/ Tidak Menikah");
        spStatus.add("Menikah");
        spStatus.add("Janda");
        spStatus.add("Duda");
        spStatus.add("Lainya");
        ArrayAdapter<String> AdapterStatus = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, spStatus);
        AdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspStatus.setAdapter(AdapterStatus);

        /*      Adapter Pendidikan Terakhir*/
        List<String> spPendidikanTerakhir = new ArrayList<String>();
        spPendidikanTerakhir.add("SD/ Sederajat");
        spPendidikanTerakhir.add("SMP/ Sederajat");
        spPendidikanTerakhir.add("SMA/ SMK/ Sederajat");
        spPendidikanTerakhir.add("D3/ S1");
        spPendidikanTerakhir.add("S2");
        spPendidikanTerakhir.add("S3");
        ArrayAdapter<String> AdapterPendidikanTerakhir = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, spPendidikanTerakhir);
        AdapterPendidikanTerakhir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspPTerakhir.setAdapter(AdapterPendidikanTerakhir);

        /*        Adapter Provinsi*/
        List<String> spProvinsi = new ArrayList<String>();
        spProvinsi.add("Aceh");
        spProvinsi.add("Bali");
        spProvinsi.add("Banten");
        spProvinsi.add("Bengkulu");
        spProvinsi.add("Gorontalo");
        spProvinsi.add("Jakarta");
        spProvinsi.add("Jambi");
        spProvinsi.add("Jawa Barat");
        spProvinsi.add("Jawa Tengah");
        spProvinsi.add("Jawa Timur");
        spProvinsi.add("Kalimantan Barat");
        spProvinsi.add("Kalilamtan Selatan");
        spProvinsi.add("Kalimantan Tengah");
        spProvinsi.add("Kalimantan Timur");
        spProvinsi.add("Kalimantan Utara");
        spProvinsi.add("Kepulauan Bangka Belitung");
        spProvinsi.add("Kepulawan Riau");
        spProvinsi.add("Lampung");
        spProvinsi.add("Maluku");
        spProvinsi.add("Maluku Utara");
        spProvinsi.add("Nusa Tenggara Barat");
        spProvinsi.add("Nusa Tenggara Timur");
        spProvinsi.add("Papua");
        spProvinsi.add("Papua Barat");
        spProvinsi.add("Riau");
        spProvinsi.add("Sulawesi Barat");
        spProvinsi.add("Sulawesi Selatan");
        spProvinsi.add("Sulawesi Tenggara");
        spProvinsi.add("Sulawesi Utara");
        spProvinsi.add("Sumatera Barat");
        spProvinsi.add("Sumatera Selatan");
        spProvinsi.add("Sumatera Utara");
        spProvinsi.add("Yogyakarta");
        ArrayAdapter<String> AdapterProvinsi = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, spProvinsi);
        AdapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspProvinsi.setAdapter(AdapterProvinsi);


    }

    private void initAdapterACT() {
        acttext_prov.setAdapter(new SuggestionProvAdapter(this, acttext_prov.getText().toString()));
        acttext_prov.setThreshold(1);
        acttext_prov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String namaProv = parent.getItemAtPosition(position).toString();
                jsonParse.searchIdProv(namaProv);
            }
        });

        acttext_kab.setAdapter(new SuggestionKabAdapter(this, acttext_prov.getText().toString(),
                acttext_kab.getText().toString()));
        acttext_kab.setThreshold(1);
        acttext_kab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String namaKab = parent.getItemAtPosition(position).toString();
                jsonParse.searchIdKab(namaKab);
            }
        });

        acttext_kec.setAdapter(new SuggestionKecAdapter(this, acttext_kab.getText().toString(),
                acttext_kec.getText().toString()));
        acttext_kec.setThreshold(1);
        acttext_kec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String namaKec = parent.getItemAtPosition(position).toString();
                jsonParse.searchIdKec(namaKec);
            }
        });

        acttext_desa.setAdapter(new SuggestionDesaAdapter(this, acttext_kec.getText().toString(),
                acttext_desa.getText().toString()));
        acttext_desa.setThreshold(1);
        acttext_desa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initComponents() {
        acttext_prov = (AutoCompleteTextView) findViewById(R.id.acttext_prov);
        acttext_kab = (AutoCompleteTextView) findViewById(R.id.acttext_kab);
        acttext_kec = (AutoCompleteTextView) findViewById(R.id.acttext_kec);
        acttext_desa = (AutoCompleteTextView) findViewById(R.id.acttext_desa);

        mbtDaftarRM = (Button) findViewById(R.id.btDaftarRM);
        mbtDaftarRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResults();
            }
        });
    }

    private void showResults() {
        String provinsi = acttext_prov.getText().toString();
        String kabupaten = acttext_kab.getText().toString();
        String kecamatan = acttext_kec.getText().toString();
        String desa = acttext_desa.getText().toString();

        Toast.makeText(this, "Provinsi : " + provinsi + "\n" +
                "Kabupaten : " + kabupaten + "\n" +
                "Kecamatan : " + kecamatan + "\n" +
                "Desa : " + desa, Toast.LENGTH_SHORT).show();
    }
}
