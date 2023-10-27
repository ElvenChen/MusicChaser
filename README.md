<h1 style="margin-bottom: 500px">Music Chaser</h1>

<p align="right"><strong>APK link:</strong> <a href="https://drive.google.com/drive/folders/1inZl41mkB2jNeb9G2qzDMWsUE6Ho1bIR">Press Here To Download!</a></p>

<div style="background-color: #f2f4f7; padding: 20px; border-radius: 10px; margin-bottom: 20px;">
    <h3 style="color: #0e75b6">Unlike mainstream music apps</br> 
      Music Chaser centers on live performances</br> 
      Providing complete event information for music enthusiasts</h3>
</div>

<div>
    <h2></h2>
</div>

<div>
    <ol>
        <li>Utilizing Build Variants to generate 2 SKUs under the same codebase.</li>
        <li>Implementing MVVM pattern as the foundation for a more robust app architecture.</li>
        <li>Leveraging Dagger Hilt to enable effective unit testing.</li>
        <li>Using Firebase Cloud Firestore as the backend data storage solution.</li>
        <li>Utilizing the Admin version app to handle music event data, meanwhile the Users version app will update instantly.</li>
        <li>Leveraging Google Calendar to create personal schedule setting of music event.</li>
    </ol>
</div>

<div>
    <h2></h2>
</div>

<div>
 <h3 align="center" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Technology implementation</h3>
    <h4>Build Variant (Application Version)</h4>
    <ul>
        <li>End-Users version</li>
        <li>Administrator Version</li>
    </ul>
    <h4>Architecture Pattern:</h4>
    <ul>
        <li>MVVM with repository</li>
        <li>Dagger Hilt</li>
    </ul>
    <h4>Networking:</h4>
    <ul>
        <li>Retrofit</li>
        <li>Moshi</li>
    </ul>
    <h4>Data Storage:</h4>
    <ul>
        <li>Firebase Cloud Firestore</li>
    </ul>
    <h4>Third-Party Libraries:</h4>
    <ul>
        <li>Maps SDK (Google map)</li>
    </ul>
</div>

<div>
    <h2></h2>
</div>

<h3 align="center" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Application Features</h3>

<h3 align="left" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Application Version:</h3>
<h4 align="left">Utilizing Build Variants to generate 2 SKUs under the same codebase.</h4>
<table align="center">
  <tr>
    <td><h4 align="center">End-Users version</h4></td>
    <td><h4 align="center">Administrator Version</h4></td>
  </tr>
  
  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/582d2f01-af3d-491c-8346-76ae4540198b" alt="End-User Version App" width="300px"></td>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/cc605aad-3464-4fc4-a9b2-088396ec4f40" alt="Admin Version App" width="300px"></td>
  </tr>
</table>

<h3 align="left" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">End-User Version Function:</h3>

<table>
  
  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/582d2f01-af3d-491c-8346-76ae4540198b" alt="Home Page pic" width="500px"></td>
    <td>
      <h3 align="left">Home Page:</h3>
      <p>Displaying popular music events and recommending artists.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Utilize an infinitely scrolling Recyclerview, users can intuitively click on individual cards to access detailed pages.</p>
    </td>
  </tr>
  
  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/6cff08eb-767c-4624-8166-dff36fe9a0a0" alt="Event Detail Page pic" width="500px"></td>
    <td>
      <h3 align="left">Event Detail Page:</h3>
      <p>Displaying details of music events, including time, location, performer information, and offering features such as map marking, event collecting and google calendar integration.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Retrieve backend data through the Firebase API, marking location on Google Maps with the Maps SDK, and using Google Calendar to create personal schedule setting.</p>
    </td>
  </tr>
  
  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/0a6adc40-50fd-4ea3-adef-df94da4d3ddd" alt="Aritst Detail Page pic" width="500px"></td>
    <td>
      <h3 align="left">Aritst Detail Page:</h3>
      <p>Presenting artist information, including a brief intro, up-coming participation event, and offering a feature for users to collect their favorite artists.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Dialog layout design to display artist detail information</p>
    </td>
  </tr>

  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/f2cd51f6-6245-4d99-b589-e9553e2426cd" alt="Society Detail Page pic" width="500px"></td>
    <td>
      <h3 align="left">Society Detail Page:</h3>
      <p>The community feature allows users to engage with others through activities like open discussions thread and leaving comments.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Utilize Firebase Cloud Firebase to store topic articles and comments which provide by users.</p>
    </td>
  </tr>
</table>

<h3 align="left" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Administrator Version Function:</h3>

<table>
  
  <tr>
    <td align="center">
      <img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/3825b7b2-3060-4fe0-9e81-f1bb92a307d5" alt="Event Management Page pic" width="240px">
      <img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/570fd285-1ef2-471b-9e60-2a99ee0a679e" alt="Event Management Page pic2" width="240px">
    </td>
    <td>
      <h3 align="left">Event Management Page:</h3>
      <p>Providing event management functions for addition, deletion, modification, and retrieval, while also offering the ability to edit the participating performers for each event.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Use Firebase Cloud Firestore as the backend data storage solution, with the ability to perform Create, Read, Update, and Delete (CRUD) operations on the data.</p>
    </td>
  </tr>

  <tr>
    <td align="center">
      <img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/0aaa1cfd-4d60-465e-bf53-5085b0136b19" alt="Artist Management Page pic2" width="240px">
      <img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/2ccc1a7b-6559-4de4-bb65-a63795c42646" alt="Artist Management Page pic" width="240px">
    </td>
    <td>
      <h3 align="left">Artist Management Page:</h3>
      <p>Providing artist management functions for addition, deletion, modification, and retrieval.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Use Firebase Cloud Firestore as the backend data storage solution, with the ability to perform Create, Read, Update, and Delete (CRUD) operations on the data.</p>
    </td>
  </tr>

  <tr>
    <td align="center"><img src="https://github.com/ElvenChen/MusicChaser/assets/134199087/9d98710b-b172-4cc6-94e8-f8575acf2710" alt="User Management Page pic" width="240px"></td>
    <td>
      <h3 align="left">User Management Page:</h3>
      <p>Integrate user management functions to govern the app aurthority of end-users.</p>
      <p><strong>Technical Implementation:</strong></p>
      <p>Utilize this functionality to meet the user management scenarios on the backend administrative system.</p>
    </td>
  </tr>
  
</table>

<div>
    <h2></h2>
</div>

<h3 align="center" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Installation Variable Setting</h3>

<h3 align="left" style="background-color: #e9ecef; padding: 20px; border-radius: 10px; margin-bottom: 20px;">Firebase cloud firestore:</h3>
<p align="left">This project utilizes Firebase Cloud Firestore for data storage and retrieval. To ensure the proper operation of the project, you will need to use the API key for Firebase Cloud Firestore. This key will be used for authentication and authorization to access the Firebase Cloud Firestore service.</p>

<div>
    <ol>
        <li>Sign in or create a Firebase account.</li>
        <li>Create a Firebase project: In the Firebase Console, create a new Firebase project and associate it with the project.</li>
        <li>Obtain the API key.</li>
        <li>Configure environment variables: In the project, ensure that you set the Firebase Cloud Firestore API key as an environment variable so that the project can access it.</li>
    </ol>
</div>
