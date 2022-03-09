      // for pie chart
      var myChartOne = document.getElementById('myChartOne');

     // var a1 = document.getElementById

      var barChart = new Chart(myChartOne, {
          type : 'pie', //pie, line, doughnut, polarArea
          data : {
              labels : ['TAX', 'INVESTMENT', 'PROFIT'],
              datasets : [{
                  labels : '바울랩 매출액',
                  data : [
                      1636250,
                      4500000,
                      5500000
                  ],
                  backgroundColor:[
                      'rgba(0, 0, 0, 0.4)',
                      'rgba(0,0,0,0.6)',
                      'rgba(0,0,0,0.8)'
                  ],
                  borderColor:'rgba(255,255,255,1)',
                  hoverBorderWidth: 5,
                  hoverOffset: 8
              }]
          },
          options: {
        plugins: {
            title: {
                display: true,
                text: 'Custom Chart Title',
            },
            legend: {
                display: true,


            }
        }
    }
});


