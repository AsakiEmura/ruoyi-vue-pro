import request from '@/utils/request'

// 创建发票信息
export function createInvoice(data) {
  return request({
    url: '/itaxbill/invoice/create',
    method: 'post',
    data: data
  })
}

// 更新发票信息
export function updateInvoice(data) {
  return request({
    url: '/itaxbill/invoice/update',
    method: 'put',
    data: data
  })
}

// 删除发票信息
export function deleteInvoice(id) {
  return request({
    url: '/itaxbill/invoice/delete?id=' + id,
    method: 'delete'
  })
}

// 获得发票信息
export function getInvoice(id) {
  return request({
    url: '/itaxbill/invoice/get?id=' + id,
    method: 'get'
  })
}

// 获得发票信息分页
export function getInvoicePage(query) {
  return request({
    url: '/itaxbill/invoice/page',
    method: 'get',
    params: query
  })
}

// 导出发票信息 Excel
export function exportInvoiceExcel(query) {
  return request({
    url: '/itaxbill/invoice/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
