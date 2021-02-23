let url = ["https://occ-0-4609-784.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABYdyeUgYLMcpTVkNMvtllFTeZH7s_nLazJTHXXQ2rXMnRh-fEvvhuqHn5aRppDAQ53x3X20osRqfjVGMx2ovPW-zJqmF.webp?r=f9b"
            ,"https://occ-0-4609-784.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABVtDBJz5g8ny3OAkB0zuoRGnU6LifXcmVSqdqlelkyvnKIeIgxCUS9ZBjlV0iVJKGRP36adJJ4N-vaSXimUd_XDC8J9P.webp?r=8f8"
            ,"https://occ-0-4609-784.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABYzzCljfxDVKp66IQYtNihnxXzl2-G8zmkSKD9WgwDrvVhbnE9rDa813saNalMpWKu8d1FLbmTpk9-Aio7yO8HFIZ6z4.webp?r=5c6"];
var i = 0
$(function(){
    $('header img').attr('src',url[i]);
    setInterval(function(){
        if(i < url.length){
            $('header img').attr('src',url[i]);
            i++
        }else{
            i=0;
            $('header img').attr('src',url[i]);
        }
       
    },4000);

    

});
