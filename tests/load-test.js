import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    scenarios: {
      default: {
        executor: 'ramping-vus',
        startVUs: 0,
        stages: [
          { duration: '30s', target: 100 },
          { duration: '1m', target: 1200 },
          { duration: '30s', target: 0 },
        ],
        gracefulRampDown: '30s',
        gracefulStop: '30s',
      },
    },
  };

export default function () {
    let res = http.get('https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/paises');
    //let res = http.get('http://localhost:8080/api/paises');
    check(res, {
        'status was 200': (r) => r.status == 200,
    });
    sleep(1);
}
