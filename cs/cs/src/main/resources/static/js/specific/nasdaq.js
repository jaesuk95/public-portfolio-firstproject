// for initial condition

        // var symbol = document.getElementById("myInput").value;
        var apiKey = "e855f115dc614499a9238f00f69e790e"

        const url = `https://api.twelvedata.com/time_series?symbol=ndaq&interval=1month&outputsize=100&apikey=${apiKey}`;

        // const url = `https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&apikey=demo`;

        fetch(url)
            .then(response => response.json())
            .then((Data) => {
                console.log("data transmission successful")
                handlerFunction(Data)
            }) // .then((Data) => {

            function handlerFunction(Data){
            document.querySelector("#chartReport").innerHTML = '<canvas id="myChart"></canvas>';
            // console.log(Data)
            console.log(Data.meta)

            const currency = Data.meta.currency
            const exchange = Data.meta.exchange
            const ex_timezone = Data.meta.exchange_timezone
            const tickerSymbol = Data.meta.symbol
            console.log(currency, exchange, ex_timezone, tickerSymbol)

            const cp = Data.values.map( (x) => x.close)
            const dt = Data.values.map( (x) => x.datetime)

            var closePrice = cp.reverse()
            var dateTime = dt.reverse()

            xlabels = dateTime
            ylabels = closePrice
            console.log(xlabels, ylabels)

            var ctx = document.getElementById('myChart').getContext("2d")

            var gradient = ctx.createLinearGradient(0, 0, 0, 400);
            gradient.addColorStop(0, 'rgba(10, 66, 250, 1)');
            gradient.addColorStop(1, 'rgba(209, 220, 255, 0.3)');

            // tooltip // https://www.chartjs.org/docs/3.5.0/samples/tooltip/content.html
            const footer = (tooltipItems) => {
                let sum = 0;

                tooltipItems.forEach(function(tooltipItem) {
                    sum += tooltipItem.parsed.y;
                });
                return 'Sum: ' + sum;
            };

            let myChartOne = document.getElementById('myChart').getContext('2d');
            // render canvas
            myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels : xlabels,
                    datasets : [{
                        labels : 'Stock price',
                        data : ylabels,
                        fill: true,
                        backgroundColor: gradient,
                        borderColor:'rgba(10, 66, 250, 0.5)',
                        pointRadius: 1,
                        tension: 0.4
                        // hoverBorderWidth: 5,
                        },] // datasets
                }, // data
                    options: {
                        interaction: {
                            intersect: false,
                            mode: 'index',
                        },
                        plugins: {
                            tooltip: {
                                callbacks: {
                                    footer: footer,
                                }
                            },
                            title: {
                                display: true,
                                text: tickerSymbol,
                            },
                            legend: {
                                display: false,
                            }   // legend
                        } // plugins: {
                    } // options: {
            });  // myChart = new Chart(ctx, {
        } // function handlerFunction(Data){

// for search condition

var xlabels = [];
var ylabels = [];

        function getInputValue(){
//            var symbol = document.getElementById("myInput").value;
            var proxyUrl = "https://cors-anywhere.herokuapp.com/";      // stand by
            var apiKey = "e855f115dc614499a9238f00f69e790e"
            var interval = document.getElementById("myInterval").value;
            const url = `https://api.twelvedata.com/time_series?symbol=dji&interval=${interval}&outputsize=150&apikey=${apiKey}`;
            // const url = `https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&apikey=demo`;

            fetch(url)
            .then(response => response.json())
            .then((Data) => {
                console.log("data transmission successful")
                handlerFunction(Data)
            }) // .then((Data) => {
        } // function getInputValue()

        function handlerFunction(Data){
            document.querySelector("#chartReport").innerHTML = '<canvas id="myChart"></canvas>';
            // console.log(Data)
            console.log(Data.meta)

            const currency = Data.meta.currency
            const exchange = Data.meta.exchange
            const ex_timezone = Data.meta.exchange_timezone
            const tickerSymbol = Data.meta.symbol
            console.log(currency, exchange, ex_timezone, tickerSymbol)

            const cp = Data.values.map( (x) => x.close)
            const dt = Data.values.map( (x) => x.datetime)

            var closePrice = cp.reverse()
            var dateTime = dt.reverse()

            xlabels = dateTime
            ylabels = closePrice
            console.log(xlabels, ylabels)

            var ctx = document.getElementById('myChart').getContext("2d")

            var gradient = ctx.createLinearGradient(0, 0, 0, 400);
            gradient.addColorStop(0, 'rgba(10, 66, 250, 1)');
            gradient.addColorStop(1, 'rgba(209, 220, 255, 0.3)');

            // tooltip // https://www.chartjs.org/docs/3.5.0/samples/tooltip/content.html
            const footer = (tooltipItems) => {
                let sum = 0;

                tooltipItems.forEach(function(tooltipItem) {
                    sum += tooltipItem.parsed.y;
                });
                return 'Sum: ' + sum;
            };

            // render canvas
            myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels : xlabels,
                    datasets : [{
                        labels : 'Stock price',
                        data : ylabels,
                        fill: true,
                        backgroundColor: gradient,
                        borderColor:'rgba(10, 66, 250, 0.5)',
                        pointRadius: 1,
                        tension: 0.4
                        // hoverBorderWidth: 5,
                        },] // datasets
                }, // data
                    options: {
                        interaction: {
                            intersect: false,
                            mode: 'index',
                        },
                        plugins: {
                            tooltip: {
                                callbacks: {
                                    footer: footer,
                                }
                            },
                            title: {
                                display: true,
                                text: tickerSymbol,
                            },
                            // scales: {
                            //     yAxes: [{
                            //             display: true,
                            //             ticks: {
                            //                 beginAtZero: true,
                            //                 steps: 10,
                            //                 stepValue: 5,
                            //                 max: 100
                            //             }
                            //         }]
                            // },
                            legend: {
                                display: false
                            }   // legend
                        } // plugins: {
                    } // options: {
            });  // myChart = new Chart(ctx, {
        } // function handlerFunction(Data){