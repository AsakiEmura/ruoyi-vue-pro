import request from '@/utils/request'

// 创建发票科目
export function createSubject(data) {
  return request({
    url: '/itaxbill/subject/create',
    method: 'post',
    data: data
  })
}

// 更新发票科目
export function updateSubject(data) {
  return request({
    url: '/itaxbill/subject/update',
    method: 'put',
    data: data
  })
}

// 删除发票科目
export function deleteSubject(id) {
  return request({
    url: '/itaxbill/subject/delete?id=' + id,
    method: 'delete'
  })
}

// 获得发票科目
export function getSubject(id) {
  return request({
    url: '/itaxbill/subject/get?id=' + id,
    method: 'get'
  })
}

// 获得发票科目分页
export function getSubjectPage(query) {
  return request({
    url: '/itaxbill/subject/page',
    method: 'get',
    params: query
  })
}

// 导出发票科目 Excel
export function exportSubjectExcel(query) {
  return request({
    url: '/itaxbill/subject/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
