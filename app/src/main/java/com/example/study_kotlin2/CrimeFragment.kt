package com.example.study_kotlin2

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import java.io.File
import java.util.*

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_CODE = 0
private const val REQUEST_CONTACT = 1
private const val REQUEST_PHOTO = 2
private const val DATE_FORMAT = "yyyy년 M월 d일 H시 m분, E요일"

class CrimeFragment : Fragment() , DatePickerFragment.Callbacks{

    private lateinit var crime: Crime
    private lateinit var photoFile : File
    private lateinit var photoUri: Uri
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var reportButton : Button
    private lateinit var suspectButton: Button
    private lateinit var photoButton: ImageButton
    private lateinit var photoView : ImageView

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    //Callback을 통해 DatePickerFragment에 데이터를 전달한다.
    override fun onDateSelected(date: Date) {
        crime.date = date
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
        //Fragment에서 인자를 불러옴
        val crimeId : UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG, "args bundle crime ID : $crimeId")
        crimeDetailViewModel.loadCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        reportButton = view.findViewById(R.id.crime_report) as Button
        suspectButton = view.findViewById(R.id.crime_suspend) as Button
        photoButton = view.findViewById(R.id.crime_camera) as ImageButton
        photoView = view.findViewById(R.id.crime_photo) as ImageView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer{crime ->
                crime?.let{
                    this.crime = crime
                    photoFile = crimeDetailViewModel.getPhotoFile(crime)
                    photoUri = FileProvider.getUriForFile(requireContext(), "com.example.study_kotlin2.fileprovider", photoFile)
                    updateUI()
                }
            }
        )
        }


    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher{
            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                crime.title = sequence.toString()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
        titleField.addTextChangedListener(titleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(crime.date).apply {

                setTargetFragment(this@CrimeFragment, REQUEST_CODE)

                //날짜 클릭 시 CrimeFragment를 FragmentManager를 통해 화면에 Dialog를 띄어준다.
                show(this@CrimeFragment.parentFragmentManager, DIALOG_DATE)
            }
        }

        //report 버튼을 클릭하면 상수 문자열을 인자로 받는 Intent 생성자를 사용해 암시적 인텐트를 시작시킨다.
        reportButton.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
            }.also { intent ->

                startActivity(intent)
                /*
                선택기가 나타날 수 있게 하는 방법
                val chooserIntent = Intent.createChooser(intent,getString(R.string.send_report))
                startActivity(chooserIntent)
                 */
            }
        }

        suspectButton.apply {
            val pickContactIntent =
                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            suspectButton.setOnClickListener {
                startActivityForResult(pickContactIntent, REQUEST_CONTACT)
            }
            pickContactIntent.addCategory(Intent.CATEGORY_HOME)
            //두 번째 암시적 인텐트에서 연락처 앱을 찾을 수 없을 경우를 대비해야한다.
            val packageManager: PackageManager = requireActivity().packageManager
            val resolvedActivity: ResolveInfo? = packageManager.resolveActivity(
                pickContactIntent, PackageManager.MATCH_DEFAULT_ONLY
            )
            if (resolvedActivity == null) {
                isEnabled = false
            }
        }

        photoButton.apply {
            val packageManager : PackageManager = requireActivity().packageManager

            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity : ResolveInfo ?= packageManager.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)

            if(resolvedActivity == null){
                isEnabled = false
            }

            setOnClickListener{
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities : List<ResolveInfo> = packageManager.queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY)

             for(cameraActivity in cameraActivities){
                 requireActivity().grantUriPermission(cameraActivity.activityInfo.packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
             }
                startActivityForResult(captureImage, REQUEST_PHOTO)

            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    private fun updateUI(){
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }

        if(crime.suspect.isNotEmpty()){
            suspectButton.text = crime.suspect
        }
        updatePhotoView()
    }

    private fun updatePhotoView(){
        if(photoFile.exists()){
            val bitmap = getScaledBitmap(photoFile.path, requireActivity())
            photoView.setImageBitmap(bitmap)
        }else{
            photoView.setImageDrawable(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when{
            requestCode != Activity.RESULT_OK -> return

            requestCode == REQUEST_CONTACT && data != null ->{
                val contactUri : Uri = data.data ?: return
                val queryField = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val cursor = requireActivity().contentResolver.query(contactUri, queryField, null, null, null)
                cursor?.use {
                    if(it.count == 0){
                        return
                    }
                    it.moveToFirst()
                    val suspect = it.getString(0)
                    crime.suspect = suspect
                    crimeDetailViewModel.saveCrime(crime)
                    suspectButton.text = suspect
                }
            }
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoView()
            }

        }
    }

    //문자열 네개를 생성하고 결합해 하나의 완전한 보고서 문자열로 반환하는 함수 생성
    private fun getCrimeReport() : String{
        val solvedString = if(crime.isSolved){
            getString(R.string.crime_report_solved)
        }else{
            getString(R.string.crime_report_unsolved)
        }
        val dateString = DateFormat.format(DATE_FORMAT, crime.date).toString()
        val suspect = if(crime.suspect.isBlank()){
            getString(R.string.crime_report_no_suspect)
        }else{
            getString(R.string.crime_report_suspect, crime.suspect)
        }
        return getString(R.string.crime_report, crime.title, dateString, solvedString, suspect)
    }

    companion object{
        fun newInstance(crimeId : UUID) : CrimeFragment{
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }

}