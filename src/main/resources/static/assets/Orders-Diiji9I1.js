import{g as m}from"./orderService-DbrzqkwP.js";import{r as _,s as f,b as r,d as t,F as c,e as u,k as h,o as l,t as o,f as b,i as x,m as y,x as k}from"./index-DN0Z6Bq5.js";const v={class:"orders"},C={class:"container"},E={class:"table table-bordered my-4"},w={class:"text-center"},N={class:"pagination d-flex justify-content-center pt-2"},P={class:"btn-group",role:"group"},z=["onClick"],O={__name:"Orders",setup(B){const e=_({args:{page:0,size:5},page:{index:0,totalPages:0,totalElements:0},orders:[]});f(()=>{console.log("orders",e.orders)});const g=n=>e.page.totalElements-n-e.args.size*e.page.index,i=async n=>{n!==void 0&&(e.args.page=n);const s=await m(e.args);s.status===200&&(e.orders=s.data.content,e.page.index=s.data.number,e.page.totalPages=s.data.totalPages,e.page.totalElements=s.data.totalElements)};return async function(){await i()}(),(n,s)=>{const p=h("router-link");return l(),r("div",v,[t("div",C,[t("table",E,[s[1]||(s[1]=t("thead",null,[t("tr",null,[t("th",{class:"text-center"},"번호"),t("th",null,"주문자명"),t("th",null,"결제 수단"),t("th",null,"결제 금액"),t("th",null,"결제일시"),t("th",null,"자세히 보기")])],-1)),t("tbody",null,[(l(!0),r(c,null,u(e.orders,(a,d)=>(l(),r("tr",null,[t("td",w,o(g(d)),1),t("td",null,o(a.name),1),t("td",null,o(a.payment==="card"?"카드":"무통장입금"),1),t("td",null,o(a.amount.toLocaleString())+"원",1),t("td",null,o(a.created),1),t("td",null,[b(p,{to:`/orders/${a.id}`},{default:x(()=>s[0]||(s[0]=[y("자세히 보기")])),_:2},1032,["to"])])]))),256))])]),t("div",N,[t("div",P,[(l(!0),r(c,null,u(e.page.totalPages,(a,d)=>(l(),r("button",{class:k(["btn py-2 px-3",[e.page.index===d?"btn-primary":"btn-outline-secondary"]]),onClick:L=>i(d)},o(a),11,z))),256))])])])])}}};export{O as default};
