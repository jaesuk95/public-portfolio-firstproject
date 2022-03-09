document.addEventListener('DOMContentLoaded', () => {
// The DOMContentLoaded event fires when the initial HTML document has been completely
// loaded and parsed, without waiting for stylesheets, images, and subframes to finish loading.

    const selectDrop = document.querySelector('#nationality');
    // const selectDrop = document.getElementById('countries');

    fetch('https://restcountries.com/v3.1/all').then(res => {
      return res.json();
    }).then(countries => {
        let output = "";
        // console.log(countries);
        countries.sort(function(a,b){return a.name.common < b.name.common ? -1 : 1});

        countries.forEach(country => {
            console.log(country.name.common)
            output += `<option>${country.name.common}</option>`;
        })

        selectDrop.innerHTML = output;
    }).catch(err => {
      console.log(err);
    })


});