package com.rsusyifamedika.syifamedika.Daftar;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.rsusyifamedika.syifamedika.Poliklinik.PemesananActivity;
import com.rsusyifamedika.syifamedika.R;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DaftarRMActivity extends AppCompatActivity {
    private Spinner mspAgama, mspStatus, mspPTerakhir, mspProvinsi, mspTanggungan, mspKabKota, mspKabKalsel, mspKecamatan;




    private Button mbtDaftarRM;
    private RadioButton mrgIdentitasDaftar;
    private EditText medNamaDaftar, medNoKartuDaftar, mEdTglLahir, medTempatLahir, medAlamatDaftar, medNoTelpDaftar, medEmailDaftar,
            medPekerjaanDaftar, medAyhDaftar, medIbuDaftar, medRadioIdentitas, medRadioPemilikID, medRadioJenisKelamin, medRadioGolonganDarah,
            medRadioKewarganegaraan, medKelurahanDaftar;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_rm);

        mspAgama = findViewById(R.id.spAgama);
        mspStatus = findViewById(R.id.spStatus);
        mspPTerakhir = findViewById(R.id.spPTerakhir);
        mspProvinsi = findViewById(R.id.spProvinsi);
        mspTanggungan = findViewById(R.id.spTanggungan);
        mspKabKota = findViewById(R.id.spKabKota);
        mspKabKalsel = findViewById(R.id.spKabKalsel);
        mspKecamatan = findViewById(R.id.spKecamatan);


        mbtDaftarRM = findViewById(R.id.btDaftarRM);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        medNamaDaftar = (EditText) findViewById(R.id.edNamaDaftar);
        medNoKartuDaftar = (EditText) findViewById(R.id.edNoKartuDaftar);
        medTempatLahir = (EditText) findViewById(R.id.edTempatLahir);
        medAlamatDaftar = (EditText) findViewById(R.id.edAlamatDaftar);
        medNoTelpDaftar = (EditText) findViewById(R.id.edNoTelpDaftar);
        medEmailDaftar = (EditText) findViewById(R.id.edEmailDaftar);
        medPekerjaanDaftar = (EditText) findViewById(R.id.edPekerjaanDaftar);
        medAyhDaftar = (EditText) findViewById(R.id.edAyhDaftar);
        medIbuDaftar = (EditText) findViewById(R.id.edIbuDaftar);
        medKelurahanDaftar = (EditText) findViewById(R.id.edKelurahanDaftar);

        //Gone
        medRadioIdentitas = (EditText) findViewById(R.id.edRadioIdentitas);
        medRadioPemilikID = (EditText) findViewById(R.id.edRadioPemilikID);
        medRadioJenisKelamin = (EditText) findViewById(R.id.edRadioJenisKelamin);
        medRadioGolonganDarah = (EditText) findViewById(R.id.edRadioGolonganDarah);
        medRadioKewarganegaraan = (EditText) findViewById(R.id.edRadioKewarganegaraan);


        mspProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        mspKabKota.setVisibility(View.VISIBLE);
                        mspKabKalsel.setVisibility(View.GONE);
                        ProvinsiAceh();
                        break;
                    case 1:
                        mspKabKota.setVisibility(View.VISIBLE);
                        mspKabKalsel.setVisibility(View.GONE);
                        ProvisnsiBali();
                        break;
                    case 2:
                        break;
                    case 3:


                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:

                        break;

                    case 11:
                        myRef.child("Provinsi").child("Kalsel").child("NamaKabupaten").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKabKota.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        ProvinsiKalsel();
                    case 12:

                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mspKabKalsel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Silahkan Pilih Kabupaten Kota", Toast.LENGTH_LONG).show();
                        break;
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mspKabKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Kab;
                Kab = parent.getSelectedItem().toString();
                switch (Kab) {
                    case "Balangan":


                        break;
                    case "Banjar":


                        break;
                    case "Barito Kuala":


                        break;
                    case "Hulu Sungai Selatan":

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mbtDaftarRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = medNamaDaftar.getText().toString().trim();
                String noID = medNoKartuDaftar.getText().toString().trim();
                String TmptLahir = medTempatLahir.getText().toString().trim();
                String Alamat = medAlamatDaftar.getText().toString().trim();
                String NoHp = medNoTelpDaftar.getText().toString().trim();
                String Email = medEmailDaftar.getText().toString().trim();
                String Pkerjaan = medPekerjaanDaftar.getText().toString().trim();
                String ibu = medIbuDaftar.getText().toString().trim();
                String ayah = medAyhDaftar.getText().toString().trim();
                String Kelurahan = medKelurahanDaftar.getText().toString().trim();


                String identitas = medRadioIdentitas.getText().toString().trim();
                String identitasPemilik = medRadioPemilikID.getText().toString().trim();
                String JenisKelamin = medRadioJenisKelamin.getText().toString().trim();
                String GolDarah = medRadioGolonganDarah.getText().toString().trim();
                String Kewarganegaraan = medRadioKewarganegaraan.getText().toString().trim();


                if (TextUtils.isEmpty(Kewarganegaraan)) {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Pilih Kewarganegaraan Anda", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(GolDarah)) {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Pilih Golongan Darah Anda", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(identitas)) {

                    Toast.makeText(getApplicationContext(),
                            "Silahkan Pilih Jenis Identitas Anda", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(identitasPemilik)) {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Pilih Kepemilikan Identitas", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(JenisKelamin)) {
                    Toast.makeText(getApplicationContext(),
                            "Silahkan Pilih Jenis Kelamin Anda", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(nama)) {
                    medNamaDaftar.setError("Nama Tidak Boleh Kosong");
                    medNamaDaftar.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(noID)) {
                    medNoKartuDaftar.setError("Nomor Identitas Tidak Boleh Kosong");
                    medNoKartuDaftar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Kelurahan)) {
                    medKelurahanDaftar.setError("Kelurahan Tidak Boleh Kosong");
                    medKelurahanDaftar.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(TmptLahir)) {
                    medTempatLahir.setError("Tempat Lahir Tidak Boleh Kosong");
                    medTempatLahir.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Alamat)) {
                    medAlamatDaftar.setError("Alamat Tidak Boleh Kosong");
                    medAlamatDaftar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(NoHp)) {
                    medNoTelpDaftar.setError("Nomor Telepon Tidak Boleh Kosong");
                    medNoTelpDaftar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Email)) {
                    medEmailDaftar.setError("Email Tidak Boleh Kosong");
                    medEmailDaftar.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    medEmailDaftar.setError("Format Email Tidak Valid");
                    medEmailDaftar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Pkerjaan)) {
                    medPekerjaanDaftar.setError("Pekerjaan Tidak Boleh Kosong");
                    medPekerjaanDaftar.requestFocus();
                    return;
                }
                DaftarNomorRM();
            }
        });
        myRef.child("TanggunganPerusahaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> MTanggungan = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Tanggungan = ds.getValue(String.class);
                    MTanggungan.add(Tanggungan);
                }
                ArrayAdapter<String> AdapterTanggungan = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item, MTanggungan);
                AdapterTanggungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspTanggungan.setAdapter(AdapterTanggungan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        spProvinsi.add("Kalilantan Selatan");
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

    private void ProvinsiKalsel(){
        mspKabKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index;
                index = parent.getSelectedItemPosition();
                switch (index) {
                    case 0:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Balangan").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;

                    case 1:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Banjar").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 2:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("BaritoKuala").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 3:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("HSS").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case 4:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("HST").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case 5:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("HSU").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case 6:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Kotabaru").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 7:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Tabalong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 8:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("TanahBumbu").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 9:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("TanahLaut").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 10:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Tapin").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 11:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Banjarbaru").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    case 12:
                        myRef.child("Provinsi").child("Kalsel").child("Kecamatan").child("Banjarmasin").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> ProvAceh = new ArrayList<String>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String Prov = ds.getValue(String.class);
                                    ProvAceh.add(Prov);
                                }
                                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mspKecamatan.setAdapter(ArrayProv);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void ProvinsiAceh() {
        myRef.child("Provinsi").child("Aceh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> ProvAceh = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String Prov = ds.getValue(String.class);
                    ProvAceh.add(Prov);
                }
                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,ProvAceh);
                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspKabKota.setAdapter(ArrayProv);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void ProvisnsiBali(){
        myRef.child("Provinsi").child("Bali").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> Provinsi = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String Prov = ds.getValue(String.class);
                    Provinsi.add(Prov);
                }
                ArrayAdapter<String> ArrayProv = new ArrayAdapter<String>(DaftarRMActivity.this, android.R.layout.simple_spinner_dropdown_item,Provinsi);
                ArrayProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspKabKota.setAdapter(ArrayProv);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void DaftarNomorRM() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Simpan");
        alertDialogBuilder
                .setMessage("Apakah Data Anda Sudah Sesuai")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("DaftarRM").child(user_id);
                        String nama = medNamaDaftar.getText().toString();
                        String NoID = medNoKartuDaftar.getText().toString();
                        String TemptLahir = medTempatLahir.getText().toString();
                        String Alamat = medAlamatDaftar.getText().toString();
                        String NoHp = medNoTelpDaftar.getText().toString();
                        String Email = medEmailDaftar.getText().toString();
                        String Pekerjaan = medPekerjaanDaftar.getText().toString();
                        String ibu = medIbuDaftar.getText().toString();
                        String ayah = medAyhDaftar.getText().toString();
                        String Kelurahan = medKelurahanDaftar.getText().toString();
                        /*Menggunakan Radio Buttom*/
                        String Identitas = medRadioIdentitas.getText().toString().trim();
                        String IdentitasPemilik = medRadioPemilikID.getText().toString().trim();
                        String JenisKelamin = medRadioJenisKelamin.getText().toString().trim();
                        String GolDarah = medRadioGolonganDarah.getText().toString().trim();
                        String Kewarga = medRadioKewarganegaraan.getText().toString().trim();
                        /*Menggunakan ComboBox*/
                        String Agama = mspAgama.getSelectedItem().toString();
                        String Status = mspStatus.getSelectedItem().toString();
                        String Pendidik = mspPTerakhir.getSelectedItem().toString();
                        String Tanggungan = mspTanggungan.getSelectedItem().toString();
                        String Provinsi = mspProvinsi.getSelectedItem().toString();
                        String Kab = mspKabKota.getSelectedItem().toString();
                        String Kecamatan = mspKecamatan.getSelectedItem().toString();


                        Map newPost = new HashMap();
                        newPost.put("nama", nama);
                        newPost.put("Identitas", NoID);
                        newPost.put("TempatLahir", TemptLahir);
                        newPost.put("Alamat", Alamat);
                        newPost.put("NoHandphone", NoHp);
                        newPost.put("Email", Email);
                        newPost.put("NamaAyah", ayah);
                        newPost.put("NamaIbu", ibu);
                        newPost.put("Pekerjaan", Pekerjaan);
                        newPost.put("Kelurahan", Kelurahan);
                        newPost.put("IdentitasJenis", Identitas);
                        newPost.put("IdentitasPemilik", IdentitasPemilik);
                        newPost.put("JenisKelamin", JenisKelamin);
                        newPost.put("GolonganDarah", GolDarah);
                        newPost.put("Kewarganegaraan", Kewarga);
                        newPost.put("Agama", Agama);
                        newPost.put("Status", Status);
                        newPost.put("PendidikanTerakhir", Pendidik);
                        newPost.put("Tanggungan", Tanggungan);
                        newPost.put("Provinsi", Provinsi);
                        newPost.put("Kabupaten",Kab);
                        newPost.put("Kecamatan",Kecamatan);
                        current_user_db.setValue(newPost);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void RadioIdentitas(View view) {
        boolean chcked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioKTP:
                if (chcked)
                    medRadioIdentitas.setText("KTP");

                break;
            case R.id.radioSIM:
                if (chcked)
                    medRadioIdentitas.setText("SIM");
                break;
            case R.id.radioPassport:
                if (chcked)
                    medRadioIdentitas.setText("Passport");
                break;

        }
    }

    public void PemilikIdentitas(View view) {
        boolean chcked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioWali:
                if (chcked)
                    medRadioPemilikID.setText("Wali");

                break;
            case R.id.aradioSendiri:
                if (chcked)
                    medRadioPemilikID.setText("Sendiri");
                break;

        }
    }

    public void RadioJenisKelamin(View view) {
        boolean chcked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioPerempuan:
                if (chcked)
                    medRadioJenisKelamin.setText("Perempuan");

                break;
            case R.id.radioLaki:
                if (chcked)
                    medRadioJenisKelamin.setText("Laki-laki");
                break;
        }
    }
    public void RadioGolonganDarah(View view) {
        boolean chcked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioA:
                if (chcked)
                    medRadioGolonganDarah.setText("A");
                break;
            case R.id.radioB:
                if (chcked)
                    medRadioGolonganDarah.setText("B");
                break;
            case R.id.radioAB:
                if (chcked)
                    medRadioGolonganDarah.setText("AB");
                break;
            case R.id.radioO:
                if (chcked)
                    medRadioGolonganDarah.setText("O");
                break;
            case R.id.radioGolLainnya:
                if (chcked)
                    medRadioGolonganDarah.setText("Lainnya");
                break;
            case R.id.radioGolTidakdiketaui:
                if (chcked)
                    medRadioGolonganDarah.setText("Tidak Diketahui");
                break;
        }
    }
    public void RadioKewarganegaraan(View view) {
        boolean chcked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioWNI:
                if (chcked)
                    medRadioKewarganegaraan.setText("WNI");
                break;
            case R.id.radioWNA:
                if (chcked)
                    medRadioKewarganegaraan.setText("WNA");
                break;
        }
    }
}

