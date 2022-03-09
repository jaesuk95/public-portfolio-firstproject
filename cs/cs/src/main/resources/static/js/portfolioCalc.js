var table = document.getElementById("table1");
        var all_tr = table.getElementsByTagName("tr");
        var all_td = table.getElementsByTagName("td");
        var apiKey = "e855f115dc614499a9238f00f69e790e";

        for (var i = 0; i<all_tr.length; i++){
            var symbol = all_tr[i].getElementsByTagName('td')[0].innerHTML;
            console.log(all_tr.length)
            console.log(symbol)
            const url = `https://api.twelvedata.com/price?symbol=${symbol}&apikey=${apiKey}`;
            // const url = `https://api.twelvedata.com/price?symbol=AAPL&apikey=demo`;
            const kospi_url = `https://api.twelvedata.com/price?symbol=usd/krw&apikey=e855f115dc614499a9238f00f69e790e`;
            handy(symbol, url, kospi_url)
        }

        // const kospi_url = `https://api.twelvedata.com/price?symbol=usd/krw&apikey=e855f115dc614499a9238f00f69e790e`;


        function handy(symbol, url, kospi_url){
            fetch(url).then(response => response.json())
                .then((Data) => {
                    console.log(Data)

                    // handlerFunction(Data)
                    var currentPrice = Data;
                    const {price} = Data;
                    var currentPrice = parseFloat(price);   // gets the current price 
                    console.log(currentPrice)
                    handy2(Data, symbol, kospi_url)
            }) 
        }

        function handy2(Data, symbol, kospi_url){
            fetch(kospi_url).then(response => response.json())
                .then((Data2) => {
                    console.log(Data)
                    console.log(symbol)
                    console.log(kospi_url)
                    console.log(Data2)

                    // handlerFunction(Data)
                    var currentPrice2 = Data2;
                    const {price} = Data2;
                    var currentPrice2 = parseFloat(price);   // gets the current price 
                    console.log(currentPrice2)
                    handlerFunction(Data, symbol, currentPrice2)
            }) 
        }


            function handlerFunction(Data, symbol, currentPrice2){
                for (var i = 0; i<all_tr.length; i++){

                    var name_ticker = all_tr[i].getElementsByTagName('td')[0].innerHTML;
                    // console.log(name_ticker)

                    var currentPrice = Data;
                    const {price} = Data;
                    var currentPrice = parseFloat(price);   // gets the current price 
                    console.log(currentPrice)

                    console.log(currentPrice2)


                    if (name_ticker == symbol){
                        console.log("surround")
                        console.log(name_ticker)
                        console.log(symbol)
                        var current_price = all_tr[i].getElementsByTagName('td')[3].innerHTML = Intl.NumberFormat().format(currentPrice.toFixed(2));
                        console.log(currentPrice)

                        var shares = all_tr[i].getElementsByTagName('td')[1].innerHTML;
                        var average_price = all_tr[i].getElementsByTagName('td')[2].innerHTML;
                        var changes = all_tr[i].getElementsByTagName('td')[4].innerHTML;
                        var netChange = all_tr[i].getElementsByTagName('td')[5].innerHTML;

                        console.log("calc")
                        console.log(currentPrice)
                        console.log(average_price)
                        console.log("calc")
                        var deltaChange = ((currentPrice-average_price)/average_price)*100
                        // // console.log(deltaChange)
                        all_tr[i].getElementsByTagName('td')[4].innerHTML = Intl.NumberFormat().format(deltaChange.toFixed(2));

                        // // netChange
                        var netChangeCal = (shares*currentPrice)-(shares*average_price)
                        all_tr[i].getElementsByTagName('td')[5].innerHTML = Intl.NumberFormat().format(netChangeCal.toFixed(2));

                        var kospi = all_tr[i].getElementsByTagName('td')[6].innerHTML = currentPrice2.toFixed(2);
                        console.log(kospi);

                        // total
                        var totalAllocation = (shares*currentPrice*kospi);
                        all_tr[i].getElementsByTagName('td')[7].innerHTML = Intl.NumberFormat().format(totalAllocation.toFixed(2));

                        console.log("result-------------------")
                        console.log(totalAllocation)

                    }


                }


                sumVal = 0;
                sumNet = 0;

                for (var i = 0; i<all_tr.length; i++){
                        // var sumVal = sumVal + parseInt(all_tr[i].getElementsByTagName('td')[7].innerHTML);
                        var stringWithoutComma = (all_tr[i].getElementsByTagName('td')[7].innerHTML).replace(/,/g,'')
                        // var sumVal = sumVal + parseInt(all_tr[i].getElementsByTagName('td')[7].innerHTML);
                        var sumVal = sumVal + parseInt(stringWithoutComma);
                        console.log(stringWithoutComma)
                        console.log(sumVal)

                        var stringWithoutCommaNet = (all_tr[i].getElementsByTagName('td')[5].innerHTML).replace(/,/g,'')
                        var sumNet = sumNet + parseInt(stringWithoutCommaNet);
                    }

                    console.log(sumVal)
                    console.log(sumNet)

                document.getElementById("totalAllocation").innerHTML = Intl.NumberFormat().format(sumVal);

                document.getElementById("totalNetChange").innerHTML = Intl.NumberFormat().format(sumNet);

            }