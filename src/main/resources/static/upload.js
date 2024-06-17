document.getElementById('image-upload-btn').addEventListener('click', function() {
    document.getElementById('image-upload').click();
});

document.getElementById('image-upload').addEventListener('change', function() {
    if (this.files.length > 0) {
        alert('Image selected: ' + this.files[0].name);
    }
});

document.getElementById('audio-upload-btn').addEventListener('click', function() {
    document.getElementById('audio-upload').click();
});

document.getElementById('audio-upload').addEventListener('change', function() {
    if (this.files.length > 0) {
        alert('Audio selected: ' + this.files[0].name);
    }
});

document.getElementById('save-btn').addEventListener('click', function() {
    // Get form data
    const title = document.getElementById('title').value;
    const genre = document.getElementById('genre').value;
    const description = document.getElementById('description').value;
    const audioFile = document.getElementById('audio-upload').files[0];  // 첫 번째 선택한 파일

    // Create form data object
    const formData = new FormData();
    formData.append('musicTitle', title);
    formData.append('musicGenre', genre);
    formData.append('musicLyrics', description);
    formData.append('audioFile', audioFile);

    // Send data to the server
    fetch('/api/music/audio', { // 요청 URL 수정
        method: 'POST',
        body: formData
    })
        .then(response => {
            if(response.ok) {
                alert("successfully saved");
            }
            else{
                alert("failed to save");
            }
        })
});
