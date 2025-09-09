document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const fileInput = document.getElementById('fileInput');
    const distributionType = document.getElementById('distributionType').value;
    const formData = new FormData();
    
    formData.append('file', fileInput.files[0]);
    formData.append('distributionType', distributionType);
    
    fetch('/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        alert('Dosya başarıyla yüklendi ve dağıtım gerçekleştirildi.');
    })
    .catch(error => {
        console.error('Hata:', error);
        alert('Dosya yüklenirken bir hata oluştu.');
    });
});
