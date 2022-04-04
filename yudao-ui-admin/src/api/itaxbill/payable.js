import request from '@/utils/request'

// 创建发票抬头
export function createPayable(data) {
  return request({
    url: '/itaxbill/payable/create',
    method: 'post',
    data: data
  })
}

// 更新发票抬头
export function updatePayable(data) {
  return request({
    url: '/itaxbill/payable/update',
    method: 'put',
    data: data
  })
}

// 删除发票抬头
export function deletePayable(id) {
  return request({
    url: '/itaxbill/payable/delete?id=' + id,
    method: 'delete'
  })
}

// 获得发票抬头
export function getPayable(id) {
  return request({
    url: '/itaxbill/payable/get?id=' + id,
    method: 'get'
  })
}

// 获得发票抬头分页
export function getPayablePage(query) {
  return request({
    url: '/itaxbill/payable/page',
    method: 'get',
    params: query
  })
}

// 导出发票抬头 Excel
export function exportPayableExcel(query) {
  return request({
    url: '/itaxbill/payable/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
