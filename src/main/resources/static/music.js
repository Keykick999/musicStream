document.addEventListener('DOMContentLoaded', function() {
    loadTracks();
});

function loadTracks() {
    fetch('http://localhost:8080/api/music/member/user001')
        .then(response => response.json())
        .then(data => {
            const tracksList = document.getElementById('tracks-list');
            data.forEach(track => {
                const trackElement = document.createElement('div');
                trackElement.className = 'track';

                const trackInfo = document.createElement('div');
                trackInfo.className = 'track-info';

                const h3 = document.createElement('h3');
                h3.textContent = track.musicTitle;
                const audio = document.createElement('audio');

                audio.controls = true;
                const encodePath = encodeURIComponent(track.musicPath);
                audio.src = `/api/music/file/${encodePath}`; // API 경로 사용
                audio.type = "audio/mp3";

                trackInfo.appendChild(h3);
                trackInfo.appendChild(audio);

                trackElement.appendChild(trackInfo);
                tracksList.appendChild(trackElement);
            });
        })
        .catch(error => console.error('Error loading tracks:', error));
}
